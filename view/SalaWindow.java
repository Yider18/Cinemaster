package view;


import controller.SalaController;
import model.Sala;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;

public class SalaWindow extends JFrame {
    private SalaController salaController;
    private JTextField txtNumero, txtCapacidad;
    private JTextArea txtAreaSalas;

    public SalaWindow() {
        salaController = new SalaController(); 
        setTitle("Administrar Salas");
        setSize(400, 300);
        setLayout(new BorderLayout());

        
        JPanel panelFormulario = new JPanel(new GridLayout(2, 2));
        txtNumero = new JTextField();
        txtCapacidad = new JTextField();

        panelFormulario.add(new JLabel("Número de Sala:"));
        panelFormulario.add(txtNumero);
        panelFormulario.add(new JLabel("Capacidad:"));
        panelFormulario.add(txtCapacidad);

        
        JButton btnAgregar = new JButton("Agregar Sala");
        btnAgregar.addActionListener(e -> agregarSala());

        
        txtAreaSalas = new JTextArea();
        txtAreaSalas.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaSalas);

        
        JButton btnListar = new JButton("Listar Salas");
        btnListar.addActionListener(e -> listarSalas());

        
        add(panelFormulario, BorderLayout.NORTH);
        add(btnAgregar, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        add(btnListar, BorderLayout.EAST);
    }

    private void agregarSala() {
        try {
            int numero = Integer.parseInt(txtNumero.getText());
            int capacidad = Integer.parseInt(txtCapacidad.getText());

            Sala sala = new Sala(0, numero, capacidad);
            salaController.agregarSala(sala);  

            JOptionPane.showMessageDialog(this, "Sala agregada exitosamente.");
            limpiarFormulario();
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar sala: " + e.getMessage());
        }
    }

    private void listarSalas() {
        try {
            txtAreaSalas.setText(""); 
            Map<Integer, Sala> salas = salaController.obtenerSalas();  
            salas.forEach((id, sala) -> {
                txtAreaSalas.append("ID: " + id + ", Número: " + sala.getNumero() + ", Capacidad: " + sala.getCapacidad() + "\n");
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar salas: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtNumero.setText("");
        txtCapacidad.setText("");
    }
}
