package view;

import controller.FacturaController;
import model.Factura;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class FacturaWindow extends JFrame {
    private FacturaController facturaController;
    private JTextField txtReservaId;
    private JTextField txtMontoTotal; // Campo de solo lectura para mostrar el monto calculado
    private JTextArea txtAreaFacturas;

    public FacturaWindow() {
        facturaController = new FacturaController();
        setTitle("Administrar Facturas");
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2));
        txtReservaId = new JTextField();
        
        // Campo de monto total en modo solo lectura
        txtMontoTotal = new JTextField();
        txtMontoTotal.setEditable(false); // Monto total calculado, no editable por el usuario

        panelFormulario.add(new JLabel("ID Reserva:"));
        panelFormulario.add(txtReservaId);
        panelFormulario.add(new JLabel("Monto Total:"));
        panelFormulario.add(txtMontoTotal);

        // Botón para agregar factura
        JButton btnAgregar = new JButton("Agregar Factura");
        btnAgregar.addActionListener(e -> agregarFactura());

        // Área de texto para mostrar las facturas
        txtAreaFacturas = new JTextArea();
        txtAreaFacturas.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaFacturas);

        // Botón para listar facturas
        JButton btnListar = new JButton("Listar Facturas");
        btnListar.addActionListener(e -> listarFacturas());

        // Agregar componentes a la ventana
        add(panelFormulario, BorderLayout.NORTH);
        add(btnAgregar, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        add(btnListar, BorderLayout.EAST);
    }

    private void agregarFactura() {
        try {
            int reservaId = Integer.parseInt(txtReservaId.getText());
            Timestamp fechaFactura = new Timestamp(System.currentTimeMillis());

            // Crear factura sin monto total, se calculará automáticamente en el controlador
            Factura factura = new Factura(reservaId, fechaFactura);

            // Llamar al controlador para calcular y agregar la factura
            facturaController.agregarFacturaConCalculo(factura);

            // Mostrar el monto total calculado en el campo de solo lectura
            txtMontoTotal.setText(factura.getMontoTotal().toString());

            JOptionPane.showMessageDialog(this, "Factura agregada exitosamente.");
            limpiarFormulario();
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar factura: " + e.getMessage());
        }
    }

    private void listarFacturas() {
        try {
            List<Factura> facturas = facturaController.obtenerFacturas();
            txtAreaFacturas.setText("");
            for (Factura factura : facturas) {
                txtAreaFacturas.append("ID: " + factura.getId() +
                        ", Reserva ID: " + factura.getReservaId() +
                        ", Monto Total: " + factura.getMontoTotal() +
                        ", Fecha: " + factura.getFechaFactura() + "\n");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar facturas: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtReservaId.setText("");
        txtMontoTotal.setText(""); // Limpiar el campo de solo lectura
    }
}
