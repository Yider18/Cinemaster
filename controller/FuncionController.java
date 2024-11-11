package controller;

import model.Funcion;
import model.FuncionDAO;

import java.sql.SQLException;
import java.util.Map;

public class FuncionController {
    private FuncionDAO funcionDAO;

    public FuncionController() {
        funcionDAO = new FuncionDAO();
    }

    public void agregarFuncion(Funcion funcion) throws SQLException {
        funcionDAO.insertarFuncion(funcion);
    }

    public Map<Integer, Funcion> obtenerFunciones() throws SQLException {
        return funcionDAO.obtenerFunciones();
    }

    public void actualizarFuncion(Funcion funcion) throws SQLException {
        funcionDAO.actualizarFuncion(funcion);
    }

    public void eliminarFuncion(int id) throws SQLException {
        funcionDAO.eliminarFuncion(id);
    }
}
