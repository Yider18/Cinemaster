package view;

import controller.FuncionController;
import model.Funcion;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class FuncionWindow extends JFrame {
    private FuncionController funcionController;
    private JTextField txtPeliculaId, txtSalaId, txtFecha, txtHora, txtPrecio, txtFuncionIdEliminar;
    private JTextArea txtAreaFunciones;

    // Obtener la fecha y hora actuales y formatearlas
    String fechaActual = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    String horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

    public FuncionWindow() {
        funcionController = new FuncionController(); // Inicializamos el controlador
        setTitle("Administrar Funciones");
        setSize(500, 500);
        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2));  // Ajuste para incluir el campo de eliminación
        txtPeliculaId = new JTextField();
        txtSalaId = new JTextField();
        txtFecha = new JTextField(fechaActual);  // Formato: YYYY-MM-DD
        txtHora = new JTextField(horaActual);   // Formato: HH:MM:SS
        txtPrecio = new JTextField();
        txtFuncionIdEliminar = new JTextField(); // Campo para el ID de función a eliminar

        panelFormulario.add(new JLabel("ID Película:"));
        panelFormulario.add(txtPeliculaId);
        panelFormulario.add(new JLabel("ID Sala:"));
        panelFormulario.add(txtSalaId);
        panelFormulario.add(new JLabel("Fecha (YYYY-MM-DD):"));
        panelFormulario.add(txtFecha);
        panelFormulario.add(new JLabel("Hora (HH:MM:SS):"));
        panelFormulario.add(txtHora);
        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(txtPrecio);

        // Campo para el ID de función a eliminar
        panelFormulario.add(new JLabel("ID Función a Eliminar:"));
        panelFormulario.add(txtFuncionIdEliminar);

        // Botón para agregar función
        JButton btnAgregar = new JButton("Agregar Función");
        btnAgregar.addActionListener(e -> agregarFuncion());

        // Botón para eliminar función
        JButton btnEliminar = new JButton("Eliminar Función");
        btnEliminar.addActionListener(e -> eliminarFuncion());

        // Área de texto para mostrar la lista de funciones
        txtAreaFunciones = new JTextArea();
        txtAreaFunciones.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaFunciones);

        // Botón para listar funciones
        JButton btnListar = new JButton("Listar Funciones");
        btnListar.addActionListener(e -> listarFunciones());

        // Agregar componentes a la ventana
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);

        add(panelFormulario, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void agregarFuncion() {
        try {
            int peliculaId = Integer.parseInt(txtPeliculaId.getText());
            int salaId = Integer.parseInt(txtSalaId.getText());
            Date fecha = Date.valueOf(txtFecha.getText());
            Time hora = Time.valueOf(txtHora.getText());
            BigDecimal precio = new BigDecimal(txtPrecio.getText());

            Funcion funcion = new Funcion(0, peliculaId, salaId, fecha, hora, precio);
            funcionController.agregarFuncion(funcion);  

            JOptionPane.showMessageDialog(this, "Función agregada exitosamente.");
            limpiarFormulario();
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar función: " + e.getMessage());
        }
    }

    private void listarFunciones() {
        try {
            txtAreaFunciones.setText(""); 
            Map<Integer, Funcion> funciones = funcionController.obtenerFunciones();  
            funciones.forEach((id, funcion) -> {
                txtAreaFunciones.append("ID: " + id + ", Película ID: " + funcion.getPeliculaId() +
                        ", Sala ID: " + funcion.getSalaId() + ", Fecha: " + funcion.getFecha() +
                        ", Hora: " + funcion.getHora() + ", Precio: " + funcion.getPrecio() + "\n");
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar funciones: " + e.getMessage());
        }
    }

    private void eliminarFuncion() {
        try {
            int funcionId = Integer.parseInt(txtFuncionIdEliminar.getText()); // Obtener el ID de función a eliminar

            // Llamar al controlador para eliminar la función
            funcionController.eliminarFuncion(funcionId);

            JOptionPane.showMessageDialog(this, "Función con ID " + funcionId + " eliminada exitosamente.");
            txtFuncionIdEliminar.setText("");  // Limpiar el campo de texto
            listarFunciones();  // Refrescar la lista de funciones
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar la función: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID de función válido.");
        }
    }

    private void limpiarFormulario() {
        txtPeliculaId.setText("");
        txtSalaId.setText("");
        txtFecha.setText(fechaActual);  // Restablecer a la fecha actual
        txtHora.setText(horaActual);    // Restablecer a la hora actual
        txtPrecio.setText("");
        txtFuncionIdEliminar.setText("");
    }
}
