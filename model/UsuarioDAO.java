// model/UsuarioDAO.java
package model;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UsuarioDAO {
    // Método para insertar un nuevo usuario
    public void insertarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, correo, telefono) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
        PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getCorreo());
            pstmt.setString(3, usuario.getTelefono());
            pstmt.executeUpdate();

            // Obtener y asignar el ID generado al usuario
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                usuario.setId(generatedKeys.getInt(1));
            }
            System.out.println("Usuario insertado con ID: " + usuario.getId());
        }
    }

    // Método para obtener todos los usuarios
    public Map<Integer, Usuario> obtenerUsuarios() throws SQLException {
        Map<Integer, Usuario> usuarios = new HashMap<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conexion = ConexionBD.obtenerConexion();
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("telefono")
                );
                usuarios.put(usuario.getId(), usuario);
            }
        }
        return usuarios;
    }

    // Método para actualizar un usuario
    public void actualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, correo = ?, telefono = ? WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getCorreo());
            pstmt.setString(3, usuario.getTelefono());
            pstmt.setInt(4, usuario.getId());
            pstmt.executeUpdate();
            System.out.println("Usuario actualizado con ID: " + usuario.getId());
        }
    }

    // Método para eliminar un usuario
    public void eliminarUsuario(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Usuario eliminado con ID: " + id);
        }
    }
}
