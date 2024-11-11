package view;

import controller.EstadisticasController;
import model.Estadistica;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;

public class EstadisticasWindow extends JFrame {
    private EstadisticasController estadisticasController;
    private JTextArea txtAreaEstadisticas;
    private JTextField txtFuncionIdEliminar;  // Campo para ingresar el ID de la función a eliminar

    public EstadisticasWindow() {
        estadisticasController = new EstadisticasController();
        setTitle("Ver Estadísticas");
        setSize(500, 500);
        setLayout(new BorderLayout());

        // Panel de botones para seleccionar el tipo de estadísticas y eliminar por función
        JPanel panelBotones = new JPanel();
        JButton btnEstadisticasPorFuncion = new JButton("Estadísticas por Función");
        JButton btnEstadisticasGenerales = new JButton("Estadísticas Generales");
        JButton btnEliminarEstadistica = new JButton("Eliminar Estadística");
        JButton btnEliminarTodas = new JButton("Eliminar Todas las Estadísticas");

        // Campo de entrada para el ID de la función a eliminar
        txtFuncionIdEliminar = new JTextField(5);

        // Configurar eventos para los botones
        btnEstadisticasPorFuncion.addActionListener(e -> listarEstadisticasPorFuncion());
        btnEstadisticasGenerales.addActionListener(e -> listarEstadisticaGeneral());
        btnEliminarEstadistica.addActionListener(e -> eliminarEstadisticaPorFuncion());
        btnEliminarTodas.addActionListener(e -> eliminarEstadisticasGenerales());

        // Agregar botones y campo de entrada al panel
        panelBotones.add(btnEstadisticasPorFuncion);
        panelBotones.add(btnEstadisticasGenerales);
        panelBotones.add(new JLabel("Función ID:"));
        panelBotones.add(txtFuncionIdEliminar);
        panelBotones.add(btnEliminarEstadistica);
        panelBotones.add(btnEliminarTodas);

        // Área de texto para mostrar las estadísticas
        txtAreaEstadisticas = new JTextArea();
        txtAreaEstadisticas.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaEstadisticas);

        // Agregar componentes a la ventana
        add(panelBotones, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void listarEstadisticasPorFuncion() {
        try {
            // Generar o actualizar estadísticas a partir de las facturas
            estadisticasController.generarEstadisticas();

            // Obtener estadísticas por función
            Map<Integer, Estadistica> estadisticas = estadisticasController.obtenerEstadisticasPorFuncion();
            txtAreaEstadisticas.setText("Estadísticas por Función:\n");

            // Mostrar las estadísticas en el área de texto
            for (Estadistica estadistica : estadisticas.values()) {
                txtAreaEstadisticas.append("Función ID: " + estadistica.getFuncionId() +
                                        ", Reservas: " + estadistica.getReservasRealizadas() +
                                        ", Ingresos Totales: " + estadistica.getIngresosTotales() + "\n");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener estadísticas: " + e.getMessage());
        }
    }

    private void listarEstadisticaGeneral() {
        try {
            // Generar o actualizar estadísticas a partir de las facturas
            estadisticasController.generarEstadisticas();

            // Obtener estadísticas generales
            Estadistica estadisticaGeneral = estadisticasController.obtenerEstadisticaGeneral();
            txtAreaEstadisticas.setText("Estadísticas Generales:\n");

            // Mostrar las estadísticas generales en el área de texto
            txtAreaEstadisticas.append("Reservas Totales: " + estadisticaGeneral.getReservasRealizadas() + "\n");
            txtAreaEstadisticas.append("Ingresos Totales: " + estadisticaGeneral.getIngresosTotales() + "\n");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener estadísticas: " + e.getMessage());
        }
    }

    private void eliminarEstadisticaPorFuncion() {
        try {
            // Obtener el ID de la función desde el campo de texto
            int funcionId = Integer.parseInt(txtFuncionIdEliminar.getText());

            estadisticasController.eliminarEstadisticaPorFuncion(funcionId);

            JOptionPane.showMessageDialog(this, "Estadística para Función ID " + funcionId + " eliminada exitosamente.");

            txtFuncionIdEliminar.setText("");
            listarEstadisticasPorFuncion(); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar la estadística: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID de función válido.");
        }
    }

    private void eliminarEstadisticasGenerales(){
        try {
            estadisticasController.eliminarEstadisticasGenerales();
            JOptionPane.showMessageDialog(this, "Todas las estadísticas han sido eliminadas.");
            txtAreaEstadisticas.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar todas las estadísticas: " + e.getMessage());
        }
    }
}
