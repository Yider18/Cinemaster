// view/ReservaWindow.java
package view;

import controller.ReservaController;
import model.Reserva;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

public class ReservaWindow extends JFrame {
    private ReservaController reservaController;
    private JTextField txtUsuarioId, txtFuncionId, txtCantidadBoletos;
    private JTextArea txtAreaReservas;

    public ReservaWindow() {
        reservaController = new ReservaController(); 
        setTitle("Administrar Reservas");
        setSize(500, 400);
        setLayout(new BorderLayout());

        
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2));
        txtUsuarioId = new JTextField();
        txtFuncionId = new JTextField();
        txtCantidadBoletos = new JTextField();

        panelFormulario.add(new JLabel("ID Usuario:"));
        panelFormulario.add(txtUsuarioId);
        panelFormulario.add(new JLabel("ID Función:"));
        panelFormulario.add(txtFuncionId);
        panelFormulario.add(new JLabel("Cantidad de Boletos:"));
        panelFormulario.add(txtCantidadBoletos);

        
        JButton btnAgregar = new JButton("Agregar Reserva");
        btnAgregar.addActionListener(e -> agregarReserva());

        
        txtAreaReservas = new JTextArea();
        txtAreaReservas.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaReservas);

        JButton btnListar = new JButton("Listar Reservas");
        btnListar.addActionListener(e -> listarReservas());

        add(panelFormulario, BorderLayout.NORTH);
        add(btnAgregar, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        add(btnListar, BorderLayout.EAST);
    }

    private void agregarReserva() {
        try {
            int usuarioId = Integer.parseInt(txtUsuarioId.getText());
            int funcionId = Integer.parseInt(txtFuncionId.getText());
            int cantidadBoletos = Integer.parseInt(txtCantidadBoletos.getText());
            Timestamp fechaReserva = new Timestamp(System.currentTimeMillis()); 

            Reserva reserva = new Reserva(0, usuarioId, funcionId, cantidadBoletos, fechaReserva);
            reservaController.agregarReserva(reserva); 

            JOptionPane.showMessageDialog(this, "Reserva agregada exitosamente.");
            limpiarFormulario();
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar reserva: " + e.getMessage());
        }
    }

    private void listarReservas() {
        try {
            txtAreaReservas.setText(""); 
            Map<Integer, Reserva> reservas = reservaController.obtenerReservas(); 
            reservas.forEach((id, reserva) -> {
                txtAreaReservas.append("ID: " + id +
                        ", Usuario ID: " + reserva.getUsuarioId() +
                        ", Función ID: " + reserva.getFuncionId() +
                        ", Boletos: " + reserva.getCantidadBoletos() +
                        ", Fecha Reserva: " + reserva.getFechaReserva() + "\n");
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar reservas: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtUsuarioId.setText("");
        txtFuncionId.setText("");
        txtCantidadBoletos.setText("");
    }
}
