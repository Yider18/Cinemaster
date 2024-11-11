package view;


import controller.PeliculaController;
import model.Pelicula;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;

public class PeliculaWindow extends JFrame {
    private PeliculaController peliculaController;
    private JTextField txtTitulo, txtGenero, txtDuracion, txtClasificacion, txtDirector;
    private JTextArea txtSinopsis;
    private JTextArea txtAreaPeliculas;

    public PeliculaWindow() {
        peliculaController = new PeliculaController(); 
        setTitle("Administrar Películas");
        setSize(500, 400);
        setLayout(new BorderLayout());

        
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2));
        txtTitulo = new JTextField();
        txtGenero = new JTextField();
        txtDuracion = new JTextField();
        txtClasificacion = new JTextField();
        txtDirector = new JTextField();
        txtSinopsis = new JTextArea(3, 20);

        panelFormulario.add(new JLabel("Título:"));
        panelFormulario.add(txtTitulo);
        panelFormulario.add(new JLabel("Género:"));
        panelFormulario.add(txtGenero);
        panelFormulario.add(new JLabel("Duración (min):"));
        panelFormulario.add(txtDuracion);
        panelFormulario.add(new JLabel("Clasificación:"));
        panelFormulario.add(txtClasificacion);
        panelFormulario.add(new JLabel("Director:"));
        panelFormulario.add(txtDirector);
        panelFormulario.add(new JLabel("Sinopsis:"));
        panelFormulario.add(new JScrollPane(txtSinopsis));

        
        JButton btnAgregar = new JButton("Agregar Película");
        btnAgregar.addActionListener(e -> agregarPelicula());

        
        txtAreaPeliculas = new JTextArea();
        txtAreaPeliculas.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaPeliculas);

        
        JButton btnListar = new JButton("Listar Películas");
        btnListar.addActionListener(e -> listarPeliculas());

        
        add(panelFormulario, BorderLayout.NORTH);
        add(btnAgregar, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        add(btnListar, BorderLayout.EAST);
    }

    private void agregarPelicula() {
        try {
            String titulo = txtTitulo.getText();
            String genero = txtGenero.getText();
            int duracion = Integer.parseInt(txtDuracion.getText());
            String clasificacion = txtClasificacion.getText();
            String director = txtDirector.getText();
            String sinopsis = txtSinopsis.getText();

            Pelicula pelicula = new Pelicula(0, titulo, genero, duracion, clasificacion, director, sinopsis);
            peliculaController.agregarPelicula(pelicula);  

            JOptionPane.showMessageDialog(this, "Película agregada exitosamente.");
            limpiarFormulario();
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar película: " + e.getMessage());
        }
    }

    private void listarPeliculas() {
        try {
            txtAreaPeliculas.setText(""); 
            Map<Integer, Pelicula> peliculas = peliculaController.obtenerPeliculas();  
            peliculas.forEach((id, pelicula) -> {
                txtAreaPeliculas.append("ID: " + id + ", Título: " + pelicula.getTitulo() + ", Género: " + pelicula.getGenero() + "\n");
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar películas: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtTitulo.setText("");
        txtGenero.setText("");
        txtDuracion.setText("");
        txtClasificacion.setText("");
        txtDirector.setText("");
        txtSinopsis.setText("");
    }
}
