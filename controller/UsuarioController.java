package controller;


import model.Usuario;
import model.UsuarioDAO;

import java.sql.SQLException;
import java.util.Map;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        usuarioDAO = new UsuarioDAO();
    }

    public void agregarUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.insertarUsuario(usuario);
    }

    public Map<Integer, Usuario> obtenerUsuarios() throws SQLException {
        return usuarioDAO.obtenerUsuarios();
    }

    public void actualizarUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.actualizarUsuario(usuario);
    }

    public void eliminarUsuario(int id) throws SQLException {
        usuarioDAO.eliminarUsuario(id);
    }
}
