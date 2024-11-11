package controller;

import model.Factura;
import model.FacturaDAO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class FacturaController {
    private FacturaDAO facturaDAO;

    public FacturaController() {
        facturaDAO = new FacturaDAO();
    }

    // Método para agregar una factura con cálculo automático del monto total
    public void agregarFacturaConCalculo(Factura factura) throws SQLException {
        // Calcular el monto total basado en la reserva asociada
        BigDecimal montoTotal = facturaDAO.calcularMontoTotal(factura.getReservaId());
        factura.setMontoTotal(montoTotal);

        // Insertar factura con el monto total calculado
        facturaDAO.insertarFactura(factura);
    }

    // Método para obtener todas las facturas
    public List<Factura> obtenerFacturas() throws SQLException {
        return facturaDAO.obtenerFacturas();
    }

    // Método para actualizar una factura con cálculo automático del monto total
    public void actualizarFacturaConCalculo(Factura factura) throws SQLException {
        // Recalcular el monto total en caso de cambios en la reserva
        BigDecimal montoTotal = facturaDAO.calcularMontoTotal(factura.getReservaId());
        factura.setMontoTotal(montoTotal);

        // Actualizar factura con el monto total recalculado
        facturaDAO.actualizarFactura(factura);
    }

    // Método para eliminar una factura por ID
    public void eliminarFactura(int id) throws SQLException {
        facturaDAO.eliminarFactura(id);
    }
}
