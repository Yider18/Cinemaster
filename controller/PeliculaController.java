package controller;

import model.Pelicula;
import model.PeliculaDAO;
import java.sql.SQLException;
import java.util.Map;

public class PeliculaController {
    private PeliculaDAO peliculaDAO;

    public PeliculaController() {
        peliculaDAO = new PeliculaDAO();
    }

    public void agregarPelicula(Pelicula pelicula) throws SQLException {
        peliculaDAO.insertarPelicula(pelicula);
    }

    public Map<Integer, Pelicula> obtenerPeliculas() throws SQLException {
        return peliculaDAO.obtenerPeliculas();
    }
}

