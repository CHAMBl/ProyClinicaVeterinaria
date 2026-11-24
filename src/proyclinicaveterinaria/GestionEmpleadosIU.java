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
public class GestionEmpleadosIU extends JFrame implements ActionListener {
    

    // Atributos
    private GestorEmpleados gestorempleados;
    private Empleado usuarioactual;        // el empleado que inició sesión (no puede eliminarse a sí mismo)
    private DefaultTableModel modelotabla; // la fuente de datos que se muestran en la tabla

    private JTextField txtdni;
    private JTextField txtnombres;
    private JTextField txtapellidos;
    private JTextField txtusuario;
    private JTextField txtcontrasena; // TODO: cambiar por JPasswordField cuando se vea en clase
    private JComboBox cborol;
    private JComboBox cbodisponible;
    private JTable tablaempleados;

    public GestionEmpleadosIU(GestorEmpleados gestorempleados, Empleado usuarioactual) {
        this.gestorempleados = gestorempleados;
        this.usuarioactual = usuarioactual;

        // Configuración de la ventana
        setTitle("Gestión de Empleados");
        setSize(900, 560);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con márgenes
        JPanel panelprincipal = new JPanel();
        panelprincipal.setLayout(new BorderLayout(10, 10));
        panelprincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Parte de arriba: título, formulario y botones
        JPanel panelsuperior = new JPanel(new BorderLayout(10, 10));

        JLabel lbltitulo = new JLabel("GESTIÓN DE EMPLEADOS", SwingConstants.CENTER);
        lbltitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lbltitulo.setForeground(new Color(0, 51, 102));
        panelsuperior.add(lbltitulo, BorderLayout.NORTH);

        // Formulario: etiqueta, caja, etiqueta, caja
        JPanel panelformulario = new JPanel(new GridLayout(4, 4, 10, 10));
        panelformulario.add(new JLabel("DNI (8 dígitos):"));
        txtdni = new JTextField();
        panelformulario.add(txtdni);
        panelformulario.add(new JLabel("Nombres:"));
        txtnombres = new JTextField();
        panelformulario.add(txtnombres);
        panelformulario.add(new JLabel("Apellidos:"));
        txtapellidos = new JTextField();
        panelformulario.add(txtapellidos);
        panelformulario.add(new JLabel("Usuario:"));
        txtusuario = new JTextField();
        panelformulario.add(txtusuario);
        panelformulario.add(new JLabel("Contraseña:"));
        txtcontrasena = new JTextField();
        panelformulario.add(txtcontrasena);
        panelformulario.add(new JLabel("Rol:"));
        cborol = new JComboBox();
        cborol.setModel(new DefaultComboBoxModel(new String[]{"Administrador", "Recepcionista", "Veterinario"}));
        panelformulario.add(cborol);
        panelformulario.add(new JLabel("Disponible (solo veterinarios):"));
        cbodisponible = new JComboBox();
        cbodisponible.setModel(new DefaultComboBoxModel(new String[]{"Sí", "No"}));
        panelformulario.add(cbodisponible);
        panelformulario.add(new JLabel(""));
        panelformulario.add(new JLabel(""));
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
        tablaempleados = new JTable();
        JScrollPane panelscroll = new JScrollPane(tablaempleados);
        panelscroll.setBorder(BorderFactory.createTitledBorder("Empleados registrados"));
        panelprincipal.add(panelscroll, BorderLayout.CENTER);

        add(panelprincipal);

        // Mostrar los empleados que ya están registrados
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
        txtusuario.setText("");
        txtcontrasena.setText("");
        cborol.setSelectedIndex(0);
        cbodisponible.setSelectedIndex(0);
        txtdni.requestFocus();
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación", JOptionPane.WARNING_MESSAGE);
    }

    // Registrar un empleado nuevo
    private void grabar() {
        String dnitexto = txtdni.getText().trim();
        String nombres = txtnombres.getText().trim();
        String apellidos = txtapellidos.getText().trim();
        String usuario = txtusuario.getText().trim();
        String contrasena = txtcontrasena.getText().trim();

        // Validaciones (formulario validado)
        if (dnitexto.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarAdvertencia("Todos los campos son obligatorios.");
            return;
        }
        if (!dnitexto.matches("\\d{8}")) {
            mostrarAdvertencia("El DNI debe tener exactamente 8 dígitos.");
            return;
        }
        int dni = Integer.valueOf(dnitexto);
        if (gestorempleados.estaLleno()) {
            mostrarAdvertencia("No hay espacio para más empleados.");
            return;
        }
        if (gestorempleados.existeDni(dni, null)) {
            mostrarAdvertencia("Ya existe un empleado con ese DNI.");
            return;
        }
        if (gestorempleados.existeUsuario(usuario, null)) {
            mostrarAdvertencia("Ese usuario ya está en uso.");
            return;
        }

        // Se crea el objeto de la clase hija según el rol elegido (todos se guardan como Empleado)
        String rol = String.valueOf(cborol.getSelectedItem());
        Empleado nuevo;
        if (rol.equals("Administrador")) {
            nuevo = new Administrador(nombres, apellidos, dni);
        } else if (rol.equals("Recepcionista")) {
            nuevo = new Recepcionista(nombres, apellidos, dni);
        } else {
            boolean disponible = String.valueOf(cbodisponible.getSelectedItem()).equals("Sí");
            nuevo = new Veterinario(disponible, nombres, apellidos, dni);
        }
        nuevo.setUsuario(usuario);
        nuevo.setContraseña(contrasena);
        nuevo.setRol(rol);

        if (gestorempleados.registrar(nuevo)) {
            JOptionPane.showMessageDialog(this, "Empleado registrado correctamente.");
            llenarTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el empleado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Buscar por DNI y mostrar sus datos en el formulario
    private void buscar() {
        String dnitexto = txtdni.getText().trim();
        if (!dnitexto.matches("\\d{8}")) {
            mostrarAdvertencia("Escriba el DNI (8 dígitos) del empleado que desea buscar.");
            return;
        }
        Empleado empleado = gestorempleados.buscarPorDni(Integer.valueOf(dnitexto));
        if (empleado == null) {
            mostrarAdvertencia("No existe un empleado con ese DNI.");
            return;
        }
        txtnombres.setText(empleado.getNombres());
        txtapellidos.setText(empleado.getApellidos());
        txtusuario.setText(empleado.getUsuario());
        txtcontrasena.setText(empleado.getContraseña());
        if (empleado instanceof Administrador) {
            cborol.setSelectedIndex(0);
        } else if (empleado instanceof Recepcionista) {
            cborol.setSelectedIndex(1);
        } else {
            cborol.setSelectedIndex(2);
        }
        if (empleado.estaDisponible()) {
            cbodisponible.setSelectedIndex(0);
        } else {
            cbodisponible.setSelectedIndex(1);
        }
    }

    // Modificar un empleado (el DNI y el rol no se modifican)
    private void modificar() {
        String dnitexto = txtdni.getText().trim();
        String nombres = txtnombres.getText().trim();
        String apellidos = txtapellidos.getText().trim();
        String usuario = txtusuario.getText().trim();
        String contrasena = txtcontrasena.getText().trim();

        if (!dnitexto.matches("\\d{8}")) {
            mostrarAdvertencia("Escriba el DNI (8 dígitos) del empleado que desea modificar.");
            return;
        }
        Empleado empleado = gestorempleados.buscarPorDni(Integer.valueOf(dnitexto));
        if (empleado == null) {
            mostrarAdvertencia("No existe un empleado con ese DNI.");
            return;
        }
        if (nombres.isEmpty() || apellidos.isEmpty() || usuario.isEmpty() || contrasena.isEmpty()) {
            mostrarAdvertencia("Todos los campos son obligatorios.");
            return;
        }
        // existeUsuario(usuario, empleado): ignora al propio empleado, para que pueda conservar su usuario
        if (gestorempleados.existeUsuario(usuario, empleado)) {
            mostrarAdvertencia("Ese usuario ya está en uso por otro empleado.");
            return;
        }
        if (!String.valueOf(cborol.getSelectedItem()).equals(empleado.getRol())) {
            mostrarAdvertencia("El rol no se puede cambiar. Elimine al empleado y regístrelo de nuevo.");
            return;
        }

        empleado.setNombres(nombres);
        empleado.setApellidos(apellidos);
        empleado.setUsuario(usuario);
        empleado.setContraseña(contrasena);
        if (empleado instanceof Veterinario) {
            empleado.setDisponible(String.valueOf(cbodisponible.getSelectedItem()).equals("Sí"));
        }
        JOptionPane.showMessageDialog(this, "Empleado modificado correctamente.");
        llenarTabla();
        limpiarCampos();
    }

    private void eliminar() {
        String dnitexto = txtdni.getText().trim();
        if (!dnitexto.matches("\\d{8}")) {
            mostrarAdvertencia("Escriba el DNI (8 dígitos) del empleado que desea eliminar.");
            return;
        }
        int dni = Integer.valueOf(dnitexto);
        Empleado empleado = gestorempleados.buscarPorDni(dni);
        if (empleado == null) {
            mostrarAdvertencia("No existe un empleado con ese DNI.");
            return;
        }
        if (empleado == usuarioactual) {
            mostrarAdvertencia("No puede eliminar su propia cuenta mientras está en sesión.");
            return;
        }
        // TODO: pedir confirmación antes de eliminar (cuando se vea el cuadro de diálogo de confirmación)
        gestorempleados.eliminar(dni);
        JOptionPane.showMessageDialog(this, "Empleado eliminado correctamente.");
        llenarTabla();
        limpiarCampos();
    }

    // Vuelve a crear el modelo de la tabla con los empleados que hay en este momento
    private void llenarTabla() {
        modelotabla = new DefaultTableModel();
        tablaempleados.setModel(modelotabla);
        modelotabla.addColumn("DNI");
        modelotabla.addColumn("Nombres");
        modelotabla.addColumn("Apellidos");
        modelotabla.addColumn("Usuario");
        modelotabla.addColumn("Rol");
        modelotabla.addColumn("Disponible");

        Empleado[] lista = gestorempleados.getLista();
        for (int i = 0; i < lista.length; i++) {
            Empleado empleado = lista[i];
            String[] fila = new String[6];
            fila[0] = String.valueOf(empleado.getDni());
            fila[1] = empleado.getNombres();
            fila[2] = empleado.getApellidos();
            fila[3] = empleado.getUsuario();
            fila[4] = empleado.getRol();
            if (empleado instanceof Veterinario) {
                if (empleado.estaDisponible()) {
                    fila[5] = "Sí";
                } else {
                    fila[5] = "No";
                }
            } else {
                fila[5] = "-";
            }
            // Agregar esta fila al modelo de la tabla
            modelotabla.addRow(fila);
        }
    }
}