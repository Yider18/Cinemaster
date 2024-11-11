package model;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class FuncionDAO {
    
    public void insertarFuncion(Funcion funcion) throws SQLException {
        String sql = "INSERT INTO funciones (pelicula_id, sala_id, fecha, hora, precio) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, funcion.getPeliculaId());
            pstmt.setInt(2, funcion.getSalaId());
            pstmt.setDate(3, funcion.getFecha());
            pstmt.setTime(4, funcion.getHora());
            pstmt.setBigDecimal(5, funcion.getPrecio());
            pstmt.executeUpdate();

            // Obtener y asignar el ID generado a la función
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                funcion.setId(generatedKeys.getInt(1));
            }
            System.out.println("Función insertada con ID: " + funcion.getId());
        }
    }

    
    public Map<Integer, Funcion> obtenerFunciones() throws SQLException {
        Map<Integer, Funcion> funciones = new HashMap<>();
        String sql = "SELECT * FROM funciones";

        try (Connection conexion = ConexionBD.obtenerConexion();
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Funcion funcion = new Funcion(
                    rs.getInt("id"),
                    rs.getInt("pelicula_id"),
                    rs.getInt("sala_id"),
                    rs.getDate("fecha"),
                    rs.getTime("hora"),
                    rs.getBigDecimal("precio")
                );
                funciones.put(funcion.getId(), funcion);
            }
        }
        return funciones;
    }

    
    public void actualizarFuncion(Funcion funcion) throws SQLException {
        String sql = "UPDATE funciones SET pelicula_id = ?, sala_id = ?, fecha = ?, hora = ?, precio = ? WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, funcion.getPeliculaId());
            pstmt.setInt(2, funcion.getSalaId());
            pstmt.setDate(3, funcion.getFecha());
            pstmt.setTime(4, funcion.getHora());
            pstmt.setBigDecimal(5, funcion.getPrecio());
            pstmt.setInt(6, funcion.getId());
            pstmt.executeUpdate();
            System.out.println("Función actualizada con ID: " + funcion.getId());
        }
    }

    
    public void eliminarFuncion(int id) throws SQLException {
        String sql = "DELETE FROM funciones WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Función eliminada con ID: " + id);
        }
    }
}

