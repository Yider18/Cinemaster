// model/ReservaDAO.java
package model;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ReservaDAO {
    // Método para insertar una nueva reserva
    public void insertarReserva(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO reservas (usuario_id, funcion_id, cantidad_boletos, fecha_reserva) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, reserva.getUsuarioId());
            pstmt.setInt(2, reserva.getFuncionId());
            pstmt.setInt(3, reserva.getCantidadBoletos());
            pstmt.setTimestamp(4, reserva.getFechaReserva());
            pstmt.executeUpdate();

            // Obtener y asignar el ID generado a la reserva
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                reserva.setId(generatedKeys.getInt(1));
            }
            System.out.println("Reserva insertada con ID: " + reserva.getId());
        }
    }

    // Método para obtener todas las reservas
    public Map<Integer, Reserva> obtenerReservas() throws SQLException {
        Map<Integer, Reserva> reservas = new HashMap<>();
        String sql = "SELECT * FROM reservas";

        try (Connection conexion = ConexionBD.obtenerConexion();
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Reserva reserva = new Reserva(
                    rs.getInt("id"),
                    rs.getInt("usuario_id"),
                    rs.getInt("funcion_id"),
                    rs.getInt("cantidad_boletos"),
                    rs.getTimestamp("fecha_reserva")
                );
                reservas.put(reserva.getId(), reserva);
            }
        }
        return reservas;
    }

    // Método para actualizar una reserva
    public void actualizarReserva(Reserva reserva) throws SQLException {
        String sql = "UPDATE reservas SET usuario_id = ?, funcion_id = ?, cantidad_boletos = ?, fecha_reserva = ? WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, reserva.getUsuarioId());
            pstmt.setInt(2, reserva.getFuncionId());
            pstmt.setInt(3, reserva.getCantidadBoletos());
            pstmt.setTimestamp(4, reserva.getFechaReserva());
            pstmt.setInt(5, reserva.getId());
            pstmt.executeUpdate();
            System.out.println("Reserva actualizada con ID: " + reserva.getId());
        }
    }

    // Método para eliminar una reserva
    public void eliminarReserva(int id) throws SQLException {
        String sql = "DELETE FROM reservas WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Reserva eliminada con ID: " + id);
        }
    }
}

