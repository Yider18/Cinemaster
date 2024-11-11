package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PruebaConexion {

    public static void main(String[] args) {
        Connection conexion = null;
        try {
            // Conectar a la base de datos
            conexion = ConexionBD.obtenerConexion();

            // Realizar una consulta a la tabla peliculas
            String sql = "SELECT * FROM peliculas";
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            // Mostrar los resultados
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String genero = rs.getString("genero");
                int duracion = rs.getInt("duracion");
                String clasificacion = rs.getString("clasificacion");
                String director = rs.getString("director");
                String sinopsis = rs.getString("sinopsis");

                System.out.println("ID: " + id);
                System.out.println("Título: " + titulo);
                System.out.println("Género: " + genero);
                System.out.println("Duración: " + duracion + " minutos");
                System.out.println("Clasificación: " + clasificacion);
                System.out.println("Director: " + director);
                System.out.println("Sinopsis: " + sinopsis);
                System.out.println("=================================");
            }

            // Cerrar el ResultSet y PreparedStatement
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.err.println("Error en la consulta: " + e.getMessage());
        } finally {
            // Cerrar la conexión
            ConexionBD.cerrarConexion(conexion);
        }
    }
}
