package view;


import controller.UsuarioController;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;

public class UsuarioWindow extends JFrame {
    private UsuarioController usuarioController;
    private JTextField txtNombre, txtCorreo, txtTelefono;
    private JTextArea txtAreaUsuarios;

    public UsuarioWindow() {
        usuarioController = new UsuarioController(); // Inicializamos el controlador
        setTitle("Administrar Usuarios");
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2));
        txtNombre = new JTextField();
        txtCorreo = new JTextField();
        txtTelefono = new JTextField();

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Correo:"));
        panelFormulario.add(txtCorreo);
        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(txtTelefono);

        // Botón para agregar usuario
        JButton btnAgregar = new JButton("Agregar Usuario");
        btnAgregar.addActionListener(e -> agregarUsuario());

        // Área de texto para mostrar la lista de usuarios
        txtAreaUsuarios = new JTextArea();
        txtAreaUsuarios.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtAreaUsuarios);

        // Botón para listar usuarios
        JButton btnListar = new JButton("Listar Usuarios");
        btnListar.addActionListener(e -> listarUsuarios());

        // Agregar componentes a la ventana
        add(panelFormulario, BorderLayout.NORTH);
        add(btnAgregar, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        add(btnListar, BorderLayout.EAST);
    }

    private void agregarUsuario() {
        try {
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String telefono = txtTelefono.getText();

            Usuario usuario = new Usuario(0, nombre, correo, telefono);
            usuarioController.agregarUsuario(usuario);  // Usamos el controlador para agregar usuario

            JOptionPane.showMessageDialog(this, "Usuario agregado exitosamente.");
            limpiarFormulario();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar usuario: " + e.getMessage());
        }
    }

    private void listarUsuarios() {
        try {
            txtAreaUsuarios.setText(""); // Limpiar el área de texto
            Map<Integer, Usuario> usuarios = usuarioController.obtenerUsuarios();  // Obtener usuarios desde el controlador
            usuarios.forEach((id, usuario) -> {
                txtAreaUsuarios.append("ID: " + id + ", Nombre: " + usuario.getNombre() +
                        ", Correo: " + usuario.getCorreo() + ", Teléfono: " + usuario.getTelefono() + "\n");
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar usuarios: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
    }
}

