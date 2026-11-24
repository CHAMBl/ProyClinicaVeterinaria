/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author bonif
 */
public class GestionClientesIU extends JFrame implements ActionListener {

    // Atributos
    private GestorClientes gestorclientes;
    private DefaultTableModel modelotabla; // la fuente de datos que se muestran en la tabla

    private JTextField txtdni;
    private JTextField txtnombres;
    private JTextField txtapellidos;
    private JTextField txttelefono;
    private JTextField txtcorreo;
    private JTextField txtdireccion;
    private JTable tablaclientes;

    public GestionClientesIU(GestorClientes gestorclientes) {
        this.gestorclientes = gestorclientes;

        // Configuración de la ventana
        setTitle("Gestión de Clientes");
        setSize(900, 540);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con márgenes
        JPanel panelprincipal = new JPanel();
        panelprincipal.setLayout(new BorderLayout(10, 10));
        panelprincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Parte de arriba: título, formulario y botones
        JPanel panelsuperior = new JPanel(new BorderLayout(10, 10));

        JLabel lbltitulo = new JLabel("GESTIÓN DE CLIENTES", SwingConstants.CENTER);
        lbltitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lbltitulo.setForeground(new Color(0, 51, 102));
        panelsuperior.add(lbltitulo, BorderLayout.NORTH);

        // Formulario: etiqueta, caja, etiqueta, caja
        JPanel panelformulario = new JPanel(new GridLayout(3, 4, 10, 10));
        panelformulario.add(new JLabel("DNI (8 dígitos):"));
        txtdni = new JTextField();
        panelformulario.add(txtdni);
        panelformulario.add(new JLabel("Nombres:"));
        txtnombres = new JTextField();
        panelformulario.add(txtnombres);
        panelformulario.add(new JLabel("Apellidos:"));
        txtapellidos = new JTextField();
        panelformulario.add(txtapellidos);
        panelformulario.add(new JLabel("Teléfono:"));
        txttelefono = new JTextField();
        panelformulario.add(txttelefono);
        panelformulario.add(new JLabel("Correo electrónico:"));
        txtcorreo = new JTextField();
        panelformulario.add(txtcorreo);
        panelformulario.add(new JLabel("Dirección:"));
        txtdireccion = new JTextField();
        panelformulario.add(txtdireccion);
        panelsuperior.add(panelformulario, BorderLayout.CENTER);

        // Botones
        JPanel panelbotones = new JPanel(new GridLayout(1, 6, 10, 10));
        panelbotones.add(crearBoton("Nuevo"));
        panelbotones.add(crearBoton("Grabar"));
        panelbotones.add(crearBoton("Buscar"));
        panelbotones.add(crearBoton("Modificar"));
        panelbotones.add(crearBoton("Eliminar"));
        panelbotones.add(crearBoton("Cerrar"));
        panelsuperior.add(panelbotones, BorderLayout.SOUTH);

        panelprincipal.add(panelsuperior, BorderLayout.NORTH);

        // Tabla de consulta con barra de desplazamiento
        tablaclientes = new JTable();
        JScrollPane panelscroll = new JScrollPane(tablaclientes);
        panelscroll.setBorder(BorderFactory.createTitledBorder("Clientes registrados"));
        panelprincipal.add(panelscroll, BorderLayout.CENTER);

        add(panelprincipal);

        // Mostrar los clientes que ya están registrados
        llenarTabla();
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
        if (comando.equals("Nuevo")) {
            limpiarCampos();
        } else if (comando.equals("Grabar")) {
            grabar();
        } else if (comando.equals("Buscar")) {
            buscar();
        } else if (comando.equals("Modificar")) {
            modificar();
        } else if (comando.equals("Eliminar")) {
            eliminar();
        } else if (comando.equals("Cerrar")) {
            dispose();
        }
    }

    private void limpiarCampos() {
        txtdni.setText("");
        txtnombres.setText("");
        txtapellidos.setText("");
        txttelefono.setText("");
        txtcorreo.setText("");
        txtdireccion.setText("");
        txtdni.requestFocus();
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación", JOptionPane.WARNING_MESSAGE);
    }

    // Registrar un cliente nuevo
    private void grabar() {
        String dnitexto = txtdni.getText().trim();
        String nombres = txtnombres.getText().trim();
        String apellidos = txtapellidos.getText().trim();
        String telefono = txttelefono.getText().trim();
        String correo = txtcorreo.getText().trim();
        String direccion = txtdireccion.getText().trim();

        // Validaciones (formulario validado)
        if (dnitexto.isEmpty() || nombres.isEmpty() || apellidos.isEmpty()
                || telefono.isEmpty() || correo.isEmpty() || direccion.isEmpty()) {
            mostrarAdvertencia("Todos los campos son obligatorios.");
            return;
        }
        if (!dnitexto.matches("\\d{8}")) {
            mostrarAdvertencia("El DNI debe tener exactamente 8 dígitos.");
            return;
        }
        int dni = Integer.valueOf(dnitexto);
        if (gestorclientes.estaLleno()) {
            mostrarAdvertencia("No hay espacio para más clientes.");
            return;
        }
        if (gestorclientes.existeDni(dni, null)) {
            mostrarAdvertencia("Ya existe un cliente con ese DNI.");
            return;
        }

        Cliente nuevo = new Cliente(telefono, correo, direccion, nombres, apellidos, dni);
        if (gestorclientes.registrar(nuevo)) {
            JOptionPane.showMessageDialog(this, "Cliente registrado correctamente.");
            llenarTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Buscar por DNI y mostrar sus datos en el formulario
    private void buscar() {
        String dnitexto = txtdni.getText().trim();
        if (!dnitexto.matches("\\d{8}")) {
            mostrarAdvertencia("Escriba el DNI (8 dígitos) del cliente que desea buscar.");
            return;
        }
        Cliente cliente = gestorclientes.buscarPorDni(Integer.valueOf(dnitexto));
        if (cliente == null) {
            mostrarAdvertencia("No existe un cliente con ese DNI.");
            return;
        }
        txtnombres.setText(cliente.getNombres());
        txtapellidos.setText(cliente.getApellidos());
        txttelefono.setText(cliente.getTelefono());
        txtcorreo.setText(cliente.getCorreo());
        txtdireccion.setText(cliente.getDireccion());
    }

    // Modificar un cliente (el DNI no se modifica)
    private void modificar() {
        String dnitexto = txtdni.getText().trim();
        String nombres = txtnombres.getText().trim();
        String apellidos = txtapellidos.getText().trim();
        String telefono = txttelefono.getText().trim();
        String correo = txtcorreo.getText().trim();
        String direccion = txtdireccion.getText().trim();

        if (!dnitexto.matches("\\d{8}")) {
            mostrarAdvertencia("Escriba el DNI (8 dígitos) del cliente que desea modificar.");
            return;
        }
        Cliente cliente = gestorclientes.buscarPorDni(Integer.valueOf(dnitexto));
        if (cliente == null) {
            mostrarAdvertencia("No existe un cliente con ese DNI.");
            return;
        }
        if (nombres.isEmpty() || apellidos.isEmpty() || telefono.isEmpty() || correo.isEmpty() || direccion.isEmpty()) {
            mostrarAdvertencia("Todos los campos son obligatorios.");
            return;
        }

        cliente.setNombres(nombres);
        cliente.setApellidos(apellidos);
        cliente.setTelefono(telefono);
        cliente.setCorreo(correo);
        cliente.setDireccion(direccion);
        JOptionPane.showMessageDialog(this, "Cliente modificado correctamente.");
        llenarTabla();
        limpiarCampos();
    }

    private void eliminar() {
        String dnitexto = txtdni.getText().trim();
        if (!dnitexto.matches("\\d{8}")) {
            mostrarAdvertencia("Escriba el DNI (8 dígitos) del cliente que desea eliminar.");
            return;
        }
        int dni = Integer.valueOf(dnitexto);
        if (gestorclientes.buscarPorDni(dni) == null) {
            mostrarAdvertencia("No existe un cliente con ese DNI.");
            return;
        }
        // TODO: pedir confirmación antes de eliminar (cuando se vea el cuadro de diálogo de confirmación)
        gestorclientes.eliminar(dni);
        JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
        llenarTabla();
        limpiarCampos();
    }

    // Vuelve a crear el modelo de la tabla con los clientes que hay en este momento
    private void llenarTabla() {
        modelotabla = new DefaultTableModel();
        tablaclientes.setModel(modelotabla);
        modelotabla.addColumn("DNI");
        modelotabla.addColumn("Nombres");
        modelotabla.addColumn("Apellidos");
        modelotabla.addColumn("Teléfono");
        modelotabla.addColumn("Correo");
        modelotabla.addColumn("Dirección");

        Cliente[] lista = gestorclientes.getLista();
        for (int i = 0; i < lista.length; i++) {
            Cliente cliente = lista[i];
            String[] fila = new String[6];
            fila[0] = String.valueOf(cliente.getDni());
            fila[1] = cliente.getNombres();
            fila[2] = cliente.getApellidos();
            fila[3] = cliente.getTelefono();
            fila[4] = cliente.getCorreo();
            fila[5] = cliente.getDireccion();
            // Agregar esta fila al modelo de la tabla
            modelotabla.addRow(fila);
        }
    }
}
