
package view;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Sistema de Cine - CineMaster");
        setSize(500, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        headerPanel.setBackground(new Color(45, 45, 60));

        JLabel lblTitulo = new JLabel("Bienvenido a CineMaster");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(230, 230, 250));

        JLabel lblDescripcion = new JLabel("Gestiona películas, salas, funciones, reservas, facturas y estadísticas");
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDescripcion.setForeground(new Color(200, 200, 220));

        
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.add(lblTitulo);
        headerPanel.add(lblDescripcion);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 10, 10)); 
        buttonPanel.setBorder(new EmptyBorder(20, 40, 20, 40));
        buttonPanel.setBackground(new Color(30, 30, 50));

        
        JButton btnPeliculas = createStyledButton("Administrar Películas");
        JButton btnSalas = createStyledButton("Administrar Salas");
        JButton btnFunciones = createStyledButton("Administrar Funciones");
        JButton btnUsuarios = createStyledButton("Administrar Usuarios");
        JButton btnReservas = createStyledButton("Administrar Reservas");
        JButton btnFacturas = createStyledButton("Administrar Facturas");
        JButton btnEstadisticas = createStyledButton("Ver Estadísticas");

        
        btnPeliculas.addActionListener(e -> new PeliculaWindow().setVisible(true));
        btnSalas.addActionListener(e -> new SalaWindow().setVisible(true));
        btnFunciones.addActionListener(e -> new FuncionWindow().setVisible(true));
        btnUsuarios.addActionListener(e -> new UsuarioWindow().setVisible(true));
        btnReservas.addActionListener(e -> new ReservaWindow().setVisible(true));
        btnFacturas.addActionListener(e -> new FacturaWindow().setVisible(true));
        btnEstadisticas.addActionListener(e -> new EstadisticasWindow().setVisible(true));

        
        buttonPanel.add(btnPeliculas);
        buttonPanel.add(btnSalas);
        buttonPanel.add(btnFunciones);
        buttonPanel.add(btnUsuarios);
        buttonPanel.add(btnReservas);
        buttonPanel.add(btnFacturas);
        buttonPanel.add(btnEstadisticas);

        
        add(headerPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(80, 80, 140));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 180), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        return button;
    }

    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Iniciar la ventana principal
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }
}
