package controller;

import model.Estadistica;
import model.EstadisticasDAO;

import java.sql.SQLException;
import java.util.Map;

public class EstadisticasController {
    private EstadisticasDAO estadisticasDAO;

    public EstadisticasController() {
        estadisticasDAO = new EstadisticasDAO();
    }

    // Método para generar estadísticas automáticamente desde las facturas
    public void generarEstadisticas() throws SQLException {
        estadisticasDAO.generarEstadisticasDesdeFacturas();
    }

    // Obtener estadísticas por función (desglose por función)
    public Map<Integer, Estadistica> obtenerEstadisticasPorFuncion() throws SQLException {
        return estadisticasDAO.obtenerEstadisticasPorFuncion();
    }

    // Obtener estadísticas generales (totales combinados)
    public Estadistica obtenerEstadisticaGeneral() throws SQLException {
        return estadisticasDAO.obtenerEstadisticaGeneral();
    }

    // Eliminar una estadística específica por funcionId
    public void eliminarEstadisticaPorFuncion(int funcionId) throws SQLException {
        estadisticasDAO.eliminarEstadisticaPorFuncion(funcionId);
    }

    // Eliminar estadisticas generales
    public void eliminarEstadisticasGenerales() throws SQLException{
        estadisticasDAO.eliminarEstadisticasGenerales();
    }
}
