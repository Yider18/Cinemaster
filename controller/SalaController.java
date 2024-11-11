package controller;


import model.Sala;
import model.SalaDAO;
import java.sql.SQLException;
import java.util.Map;

public class SalaController {
    private SalaDAO salaDAO;

    public SalaController() {
        salaDAO = new SalaDAO();
    }

    public void agregarSala(Sala sala) throws SQLException {
        salaDAO.insertarSala(sala);
    }

    public Map<Integer, Sala> obtenerSalas() throws SQLException {
        return salaDAO.obtenerSalas();
    }
}

