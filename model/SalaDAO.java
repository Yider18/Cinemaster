package model;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SalaDAO {
    
    public void insertarSala(Sala sala) throws SQLException {
        String sql = "INSERT INTO salas (numero, capacidad) VALUES (?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, sala.getNumero());
            pstmt.setInt(2, sala.getCapacidad());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                sala.setId(generatedKeys.getInt(1));
            }
            System.out.println("Sala insertada exitosamente con ID: " + sala.getId());
        }
    }

    public Map<Integer, Sala> obtenerSalas() throws SQLException {
        Map<Integer, Sala> salas = new HashMap<>();
        String sql = "SELECT * FROM salas";

        try (Connection conexion = ConexionBD.obtenerConexion();
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Sala sala = new Sala(
                    rs.getInt("id"),
                    rs.getInt("numero"),
                    rs.getInt("capacidad")
                );
                salas.put(sala.getId(), sala);
            }
        }
        return salas;
    }

    public Sala obtenerSalaPorId(int id) throws SQLException {
        String sql = "SELECT * FROM salas WHERE id = ?";
        Sala sala = null;

        try (Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                sala = new Sala(
                    rs.getInt("id"),
                    rs.getInt("numero"),
                    rs.getInt("capacidad")
                );
            }
        }
        return sala;
    }
}
