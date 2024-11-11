package model;

import java.math.BigDecimal;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class EstadisticasDAO {

    // Método para generar estadísticas a partir de las facturas
    public void generarEstadisticasDesdeFacturas() throws SQLException {
        String sql = "SELECT r.funcion_id, SUM(f.monto_total) AS ingresos_totales, COUNT(r.id) AS reservas_realizadas " +
                     "FROM factura f " +
                     "JOIN reservas r ON f.reserva_id = r.id " +
                     "GROUP BY r.funcion_id";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int funcionId = rs.getInt("funcion_id");
                BigDecimal ingresosTotales = rs.getBigDecimal("ingresos_totales");
                int reservasRealizadas = rs.getInt("reservas_realizadas");

                if (existeEstadistica(funcionId)) {
                    actualizarEstadisticaPorFuncion(funcionId, reservasRealizadas, ingresosTotales);
                } else {
                    insertarEstadisticaPorFuncion(funcionId, reservasRealizadas, ingresosTotales);
                }
            }
        }
    }

    // Verifica si ya existe una estadística para la función
    private boolean existeEstadistica(int funcionId) throws SQLException {
        String sql = "SELECT id FROM estadisticas WHERE funcion_id = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, funcionId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    // Inserta una nueva estadística para una función específica
    private void insertarEstadisticaPorFuncion(int funcionId, int reservasRealizadas, BigDecimal ingresosTotales) throws SQLException {
        String sql = "INSERT INTO estadisticas (funcion_id, reservas_realizadas, ingresos_totales) VALUES (?, ?, ?)";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, funcionId);
            pstmt.setInt(2, reservasRealizadas);
            pstmt.setBigDecimal(3, ingresosTotales);
            pstmt.executeUpdate();
        }
    }

    // Actualiza una estadística existente para una función específica
    private void actualizarEstadisticaPorFuncion(int funcionId, int reservasRealizadas, BigDecimal ingresosTotales) throws SQLException {
        String sql = "UPDATE estadisticas SET reservas_realizadas = ?, ingresos_totales = ? WHERE funcion_id = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, reservasRealizadas);
            pstmt.setBigDecimal(2, ingresosTotales);
            pstmt.setInt(3, funcionId);
            pstmt.executeUpdate();
        }
    }

    // Elimina una estadística específica por funcionId
    public void eliminarEstadisticaPorFuncion(int funcionId) throws SQLException {
        String sql = "DELETE FROM estadisticas WHERE funcion_id = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, funcionId);
            pstmt.executeUpdate();
        }
    }

    // Elimina todas las estadísticas generales
    public void eliminarEstadisticasGenerales() throws SQLException {
        String sql = "DELETE FROM estadisticas";
        try (Connection conexion = ConexionBD.obtenerConexion();
             Statement stmt = conexion.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // Obtiene estadísticas por función
    public Map<Integer, Estadistica> obtenerEstadisticasPorFuncion() throws SQLException {
        Map<Integer, Estadistica> estadisticas = new HashMap<>();
        String sql = "SELECT * FROM estadisticas WHERE funcion_id IS NOT NULL";

        try (Connection conexion = ConexionBD.obtenerConexion();
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Estadistica estadistica = new Estadistica(
                    rs.getInt("id"),
                    null, 
                    rs.getInt("funcion_id"),
                    rs.getInt("reservas_realizadas"),
                    rs.getBigDecimal("ingresos_totales")
                );
                estadisticas.put(estadistica.getFuncionId(), estadistica);
            }
        }
        return estadisticas;
    }

    // Obtiene estadísticas generales (total de todas las funciones)
    public Estadistica obtenerEstadisticaGeneral() throws SQLException {
        String sql = "SELECT SUM(reservas_realizadas) AS total_reservas, SUM(ingresos_totales) AS total_ingresos FROM estadisticas";
        Estadistica estadisticaGeneral = null;

        try (Connection conexion = ConexionBD.obtenerConexion();
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                int totalReservas = rs.getInt("total_reservas");
                BigDecimal totalIngresos = rs.getBigDecimal("total_ingresos");
                estadisticaGeneral = new Estadistica(totalReservas, totalIngresos);
            }
        }
        return estadisticaGeneral;
    }
}
