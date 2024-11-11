package model;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PeliculaDAO {
    public void insertarPelicula(Pelicula pelicula) throws SQLException {
        String sql = "INSERT INTO peliculas (titulo, genero, duracion, clasificacion, director, sinopsis) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, pelicula.getTitulo());
            pstmt.setString(2, pelicula.getGenero());
            pstmt.setInt(3, pelicula.getDuracion());
            pstmt.setString(4, pelicula.getClasificacion());
            pstmt.setString(5, pelicula.getDirector());
            pstmt.setString(6, pelicula.getSinopsis());
            pstmt.executeUpdate();

            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                pelicula.setId(generatedKeys.getInt(1));
            }
            System.out.println("Película insertada exitosamente con ID: " + pelicula.getId());
        }
    }

    public Map<Integer, Pelicula> obtenerPeliculas() throws SQLException {
        Map<Integer, Pelicula> peliculas = new HashMap<>();
        String sql = "SELECT * FROM peliculas";

        try (Connection conexion = ConexionBD.obtenerConexion();
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pelicula pelicula = new Pelicula(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("genero"),
                    rs.getInt("duracion"),
                    rs.getString("clasificacion"),
                    rs.getString("director"),
                    rs.getString("sinopsis")
                );
                peliculas.put(pelicula.getId(), pelicula);  
            }
        }
        return peliculas;
    }

    public Pelicula obtenerPeliculaPorId(int id) throws SQLException {
        String sql = "SELECT * FROM peliculas WHERE id = ?";
        Pelicula pelicula = null;

        try (Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                pelicula = new Pelicula(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("genero"),
                    rs.getInt("duracion"),
                    rs.getString("clasificacion"),
                    rs.getString("director"),
                    rs.getString("sinopsis")
                );
            }
        }
        return pelicula;
    }
}
