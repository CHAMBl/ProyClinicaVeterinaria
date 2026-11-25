/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author bonif
 */
public class LoginIU extends JFrame implements ActionListener {

    // Atributos: los arreglos de objetos del sistema (se pasan de ventana en ventana, como RegistroPersona en el ejemplo)
    private GestorEmpleados gestorempleados;
    private GestorClientes gestorclientes;
    private GestorMascotas gestormascotas;
    private GestorServicios gestorservicios;
    private GestorMedicamentos gestormedicamentos;
    private GestorCitas gestorcitas;

    private JTextField txtusuario;
    private JTextField txtcontrasena; // TODO: cambiar por JPasswordField cuando se vea en clase (no está en los ejemplos)

    // Constructor 1: al abrir el programa. Crea los arreglos y el administrador inicial
    public LoginIU() {
        gestorempleados = new GestorEmpleados(50);
        gestorclientes = new GestorClientes(100);
        gestormascotas = new GestorMascotas(200);
        gestorservicios = new GestorServicios(30);
        gestormedicamentos = new GestorMedicamentos(100);
        gestorcitas = new GestorCitas(300);

        // Administrador inicial (el mismo que crea el main de consola) para poder entrar la primera vez
        Administrador admin = new Administrador("Admin", "Principal", 12345678);
        admin.setUsuario("admin");
        admin.setContraseña("admin123");
        admin.setRol("Administrador");
        gestorempleados.registrar(admin);

        armarVentana();
    }

    // Constructor 2: al cerrar sesión. Recibe los mismos arreglos para no perder los datos
    public LoginIU(GestorEmpleados gestorempleados, GestorClientes gestorclientes,
            GestorMascotas gestormascotas, GestorServicios gestorservicios,
            GestorMedicamentos gestormedicamentos, GestorCitas gestorcitas) {
        this.gestorempleados = gestorempleados;
        this.gestorclientes = gestorclientes;
        this.gestormascotas = gestormascotas;
        this.gestorservicios = gestorservicios;
        this.gestormedicamentos = gestormedicamentos;
        this.gestorcitas = gestorcitas;
        armarVentana();
    }

    private void armarVentana() {
        // Configuración de la ventana principal
        setTitle("Clínica Veterinaria - Inicio de sesión");
        setSize(420, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con márgenes
        JPanel panelprincipal = new JPanel();
        panelprincipal.setLayout(new BorderLayout(10, 10));
        panelprincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título superior
        JLabel lbltitulo = new JLabel("CLÍNICA VETERINARIA - INICIO DE SESIÓN", SwingConstants.CENTER);
        lbltitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lbltitulo.setForeground(new Color(0, 51, 102));
        panelprincipal.add(lbltitulo, BorderLayout.NORTH);

        // Panel central: usuario y contraseña
        JPanel paneldatos = new JPanel(new GridLayout(2, 2, 10, 10));
        paneldatos.add(new JLabel("Usuario:"));
        txtusuario = new JTextField();
        paneldatos.add(txtusuario);
        paneldatos.add(new JLabel("Contraseña:"));
        txtcontrasena = new JTextField();
        paneldatos.add(txtcontrasena);
        panelprincipal.add(paneldatos, BorderLayout.CENTER);

        // Panel inferior: botones
        JPanel panelbotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelbotones.add(crearBoton("Ingresar"));
        panelbotones.add(crearBoton("Salir"));
        panelprincipal.add(panelbotones, BorderLayout.SOUTH);

        add(panelprincipal);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.addActionListener(this);
        return boton;
    }

    // Se ejecuta al presionar cualquiera de los botones: el texto del botón dice cuál fue
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals("Ingresar")) {
            ingresar();
        } else if (comando.equals("Salir")) {
            System.exit(0);
        }
    }

    private void ingresar() {
        String usuario = txtusuario.getText().trim();
        String contrasena = txtcontrasena.getText().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese su usuario y su contraseña.", "Error de validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // El gestor busca al empleado que coincida con usuario y contraseña (devuelve null si no hay)
        Empleado empleado = gestorempleados.autenticar(usuario, contrasena);
        if (empleado == null) {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error de acceso", JOptionPane.ERROR_MESSAGE);
            txtcontrasena.setText("");
            return;
        }

        // Abrir el menú principal y cerrar esta ventana
        MenuPrincipalIU frmmenu = new MenuPrincipalIU(gestorempleados, gestorclientes, gestormascotas, gestorservicios,
                gestormedicamentos, gestorcitas, empleado);
        frmmenu.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        // Ejecutar la interfaz gráfica en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            new LoginIU().setVisible(true);
        });
    }
}