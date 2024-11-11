package model;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO {

    // Método para calcular el monto total de una factura
    public BigDecimal calcularMontoTotal(int reservaId) throws SQLException {
        BigDecimal montoTotal = BigDecimal.ZERO;

        String sql = "SELECT f.precio, r.cantidad_boletos FROM reservas r " +
                    "JOIN funciones f ON r.funcion_id = f.id WHERE r.id = ?";
        
        try (Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, reservaId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    BigDecimal precio = rs.getBigDecimal("precio");
                    int cantidadBoletos = rs.getInt("cantidad_boletos");

                    // Calcular el monto total como precio * cantidad de boletos
                    montoTotal = precio.multiply(BigDecimal.valueOf(cantidadBoletos));
                }
            }
        }
        return montoTotal;
    }

    // Método para insertar una factura calculando el monto total automáticamente
    public void insertarFactura(Factura factura) throws SQLException {
        // Calcular el monto total para la factura
        BigDecimal montoTotal = calcularMontoTotal(factura.getReservaId());
        factura.setMontoTotal(montoTotal);

        String sql = "INSERT INTO factura (reserva_id, monto_total, fecha_factura) VALUES (?, ?, ?)";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, factura.getReservaId());
            pstmt.setBigDecimal(2, factura.getMontoTotal());
            pstmt.setTimestamp(3, factura.getFechaFactura());
            pstmt.executeUpdate();

            // Obtener el ID generado para la factura
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                factura.setId(generatedKeys.getInt(1));
            }
        }
    }

    // Método para obtener todas las facturas
    public List<Factura> obtenerFacturas() throws SQLException {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT * FROM factura";

        try (Connection conexion = ConexionBD.obtenerConexion();
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Factura factura = new Factura(
                    rs.getInt("id"),
                    rs.getInt("reserva_id"),
                    rs.getBigDecimal("monto_total"),
                    rs.getTimestamp("fecha_factura")
                );
                facturas.add(factura);
            }
        }
        return facturas;
    }

    // Método para actualizar una factura
    public void actualizarFactura(Factura factura) throws SQLException {
        // Recalcular el monto total antes de actualizar
        BigDecimal montoTotal = calcularMontoTotal(factura.getReservaId());
        factura.setMontoTotal(montoTotal);

        String sql = "UPDATE factura SET reserva_id = ?, monto_total = ?, fecha_factura = ? WHERE id = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, factura.getReservaId());
            pstmt.setBigDecimal(2, factura.getMontoTotal());
            pstmt.setTimestamp(3, factura.getFechaFactura());
            pstmt.setInt(4, factura.getId());
            pstmt.executeUpdate();
        }
    }

    // Método para eliminar una factura
    public void eliminarFactura(int id) throws SQLException {
        String sql = "DELETE FROM factura WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
