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
public class MenuPrincipalIU extends JFrame implements ActionListener {

    // Atributos
    private GestorEmpleados gestorempleados;
    private GestorClientes gestorclientes;
    private GestorMascotas gestormascotas;
    private GestorServicios gestorservicios;
    private GestorMedicamentos gestormedicamentos;
    private GestorCitas gestorcitas;
    private GestorAtenciones gestoratenciones;
    private Empleado usuarioactual; // empleado que inició sesión

    // Ventanas que se van abriendo, para poder cerrarlas todas al cerrar sesión
    private JFrame[] ventanasabiertas = new JFrame[50];
    private int cantidadventanas = 0;

    public MenuPrincipalIU(GestorEmpleados gestorempleados, GestorClientes gestorclientes,
            GestorMascotas gestormascotas, GestorServicios gestorservicios,
            GestorMedicamentos gestormedicamentos, GestorCitas gestorcitas,
            GestorAtenciones gestoratenciones, Empleado usuarioactual) {
        this.gestorempleados = gestorempleados;
        this.gestorclientes = gestorclientes;
        this.gestormascotas = gestormascotas;
        this.gestorservicios = gestorservicios;
        this.gestormedicamentos = gestormedicamentos;
        this.gestorcitas = gestorcitas;
        this.gestoratenciones = gestoratenciones;
        this.usuarioactual = usuarioactual;

        // Configuración de la ventana principal
        setTitle("Clínica Veterinaria - Menú Principal");
        setSize(520, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Mensaje de bienvenida con los datos del empleado
        JPanel panelprincipal = new JPanel();
        panelprincipal.setLayout(new BorderLayout(10, 10));
        panelprincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lbltitulo = new JLabel("CLÍNICA VETERINARIA", SwingConstants.CENTER);
        lbltitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lbltitulo.setForeground(new Color(0, 51, 102));
        panelprincipal.add(lbltitulo, BorderLayout.NORTH);

        JPanel paneldatos = new JPanel(new GridLayout(3, 1, 5, 5));
        paneldatos.add(new JLabel("Bienvenido, " + usuarioactual.getNombreCompleto()));
        paneldatos.add(new JLabel("Rol: " + usuarioactual.getRol()));
        // getPermisos() es polimórfico: cada rol (Administrador, Recepcionista, Veterinario) devuelve su propio texto
        paneldatos.add(new JLabel("Permisos: " + usuarioactual.getPermisos()));
        panelprincipal.add(paneldatos, BorderLayout.CENTER);

        add(panelprincipal);

        // Barra de menú
        JMenuBar barramenu = new JMenuBar();

        // Menú "Archivo": lo ven todos los roles
        JMenu menuarchivo = new JMenu();
        menuarchivo.setText("Archivo");
        menuarchivo.add(crearItem("Cerrar sesión"));
        menuarchivo.add(crearItem("Salir"));
        barramenu.add(menuarchivo);

        // Menú "Gestión": solo se agregan las opciones que el rol puede usar (RF11)
        JMenu menugestion = new JMenu();
        menugestion.setText("Gestión");
        if (usuarioactual instanceof Administrador) {
            menugestion.add(crearItem("Empleados"));
            menugestion.add(crearItem("Servicios"));
            menugestion.add(crearItem("Medicamentos"));
        } else if (usuarioactual instanceof Recepcionista) {
            menugestion.add(crearItem("Clientes"));
            menugestion.add(crearItem("Mascotas"));
            menugestion.add(crearItem("Citas"));
        } else if (usuarioactual instanceof Veterinario) {
            menugestion.add(crearItem("Mis atenciones"));
            menugestion.add(crearItem("Mi disponibilidad"));
        }
        barramenu.add(menugestion);

        // TODO RF10: Administrador -> menú "Reportes"

        setJMenuBar(barramenu);
    }

    // Crea una opción de menú con su texto y la conecta con actionPerformed
    private JMenuItem crearItem(String texto) {
        JMenuItem item = new JMenuItem();
        item.setText(texto);
        item.addActionListener(this);
        return item;
    }

    // Se ejecuta al elegir cualquier opción del menú: el texto de la opción dice cuál fue
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        if (comando.equals("Empleados")) {
            abrirVentana(new GestionEmpleadosIU(gestorempleados, usuarioactual));
        } else if (comando.equals("Servicios")) {
            abrirVentana(new GestionServiciosIU(gestorservicios));
        } else if (comando.equals("Clientes")) {
            abrirVentana(new GestionClientesIU(gestorclientes));
        } else if (comando.equals("Mascotas")) {
            abrirVentana(new GestionMascotasIU(gestormascotas, gestorclientes));
        } else if (comando.equals("Medicamentos")) {
            abrirVentana(new GestionMedicamentosIU(gestormedicamentos));
        } else if (comando.equals("Citas")) {
            abrirVentana(new GestionCitasIU(gestorcitas, gestormascotas, gestorempleados, gestorservicios));
        } else if (comando.equals("Mis atenciones")) {
            abrirVentana(new GestionAtencionesIU(gestoratenciones, gestorcitas, gestormedicamentos, usuarioactual));
        } else if (comando.equals("Mi disponibilidad")) {
            cambiarDisponibilidad();
        } else if (comando.equals("Cerrar sesión")) {
            cerrarSesion();
        } else if (comando.equals("Salir")) {
            System.exit(0);
        }
    }

    // Muestra la ventana y la guarda en el arreglo
    private void abrirVentana(JFrame ventana) {
        ventana.setVisible(true);
        if (cantidadventanas < ventanasabiertas.length) {
            ventanasabiertas[cantidadventanas] = ventana;
            cantidadventanas++;
        }
    }

    // Igual que en la versión de consola: si estaba disponible pasa a no disponible, y al revés
    private void cambiarDisponibilidad() {
        boolean nuevo = !usuarioactual.estaDisponible();
        usuarioactual.setDisponible(nuevo);
        if (nuevo) {
            JOptionPane.showMessageDialog(this, "Ahora su disponibilidad es: Disponible");
        } else {
            JOptionPane.showMessageDialog(this, "Ahora su disponibilidad es: No disponible");
        }
    }

    private void cerrarSesion() {
        // Volver al login con los mismos arreglos (no se pierden los datos)
        LoginIU frmlogin = new LoginIU(gestorempleados, gestorclientes, gestormascotas, gestorservicios,
                gestormedicamentos, gestorcitas, gestoratenciones);
        frmlogin.setVisible(true);
        // Cerrar las ventanas que quedaron abiertas, para que no se usen con otro rol
        for (int i = 0; i < cantidadventanas; i++) {
            ventanasabiertas[i].dispose();
        }
        dispose();
    }
}
