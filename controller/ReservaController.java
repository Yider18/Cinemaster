package controller;

import model.Reserva;
import model.ReservaDAO;

import java.sql.SQLException;
import java.util.Map;

public class ReservaController {
    private ReservaDAO reservaDAO;

    public ReservaController() {
        reservaDAO = new ReservaDAO();
    }

    public void agregarReserva(Reserva reserva) throws SQLException {
        reservaDAO.insertarReserva(reserva);
    }

    public Map<Integer, Reserva> obtenerReservas() throws SQLException {
        return reservaDAO.obtenerReservas();
    }

    public void actualizarReserva(Reserva reserva) throws SQLException {
        reservaDAO.actualizarReserva(reserva);
    }

    public void eliminarReserva(int id) throws SQLException {
        reservaDAO.eliminarReserva(id);
    }
}
