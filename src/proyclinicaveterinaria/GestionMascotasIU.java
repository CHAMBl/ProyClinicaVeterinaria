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
public class GestionMascotasIU extends JFrame implements ActionListener {

    private GestorMascotas gestormascotas;
    private GestorClientes gestorclientes;
    private DefaultTableModel modelotabla;

    private JTextField txtcodigo;
    private JTextField txtdnidueno;
    private JTextField txtnombre;
    private JComboBox cboespecie;
    private JTextField txtraza;
    private JComboBox cbosexo;
    private JTextField txtfecha;
    private JTextField txtpeso;
    private JTable tablamascotas;

    public GestionMascotasIU(GestorMascotas gestormascotas, GestorClientes gestorclientes) {
        this.gestormascotas = gestormascotas;
        this.gestorclientes = gestorclientes;

        setTitle("Gestión de Mascotas");
        setSize(1000, 580);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelprincipal = new JPanel();
        panelprincipal.setLayout(new BorderLayout(10, 10));
        panelprincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelsuperior = new JPanel(new BorderLayout(10, 10));

        JLabel lbltitulo = new JLabel("GESTIÓN DE MASCOTAS", SwingConstants.CENTER);
        lbltitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lbltitulo.setForeground(new Color(0, 51, 102));
        panelsuperior.add(lbltitulo, BorderLayout.NORTH);

        JPanel panelformulario = new JPanel(new GridLayout(4, 4, 10, 10));
        panelformulario.add(new JLabel("Código:"));
        txtcodigo = new JTextField();
        panelformulario.add(txtcodigo);
        panelformulario.add(new JLabel("DNI del dueño (cliente):"));
        txtdnidueno = new JTextField();
        panelformulario.add(txtdnidueno);
        panelformulario.add(new JLabel("Nombre:"));
        txtnombre = new JTextField();
        panelformulario.add(txtnombre);
        panelformulario.add(new JLabel("Especie:"));
        cboespecie = new JComboBox();
        cboespecie.setModel(new DefaultComboBoxModel(new String[]{"Perro", "Gato", "Ave"}));
        panelformulario.add(cboespecie);
        panelformulario.add(new JLabel("Raza:"));
        txtraza = new JTextField();
        panelformulario.add(txtraza);
        panelformulario.add(new JLabel("Sexo:"));
        cbosexo = new JComboBox();
        cbosexo.setModel(new DefaultComboBoxModel(new String[]{"Macho", "Hembra"}));
        panelformulario.add(cbosexo);
        panelformulario.add(new JLabel("Fecha de nacimiento (dd/mm/aaaa):"));
        txtfecha = new JTextField();
        panelformulario.add(txtfecha);
        panelformulario.add(new JLabel("Peso en kg (con punto):"));
        txtpeso = new JTextField();
        panelformulario.add(txtpeso);
        panelsuperior.add(panelformulario, BorderLayout.CENTER);

        JPanel panelbotones = new JPanel(new GridLayout(1, 6, 10, 10));
        panelbotones.add(crearBoton("Nuevo"));
        panelbotones.add(crearBoton("Grabar"));
        panelbotones.add(crearBoton("Buscar"));
        panelbotones.add(crearBoton("Modificar"));
        panelbotones.add(crearBoton("Eliminar"));
        panelbotones.add(crearBoton("Cerrar"));
        panelsuperior.add(panelbotones, BorderLayout.SOUTH);

        panelprincipal.add(panelsuperior, BorderLayout.NORTH);

        tablamascotas = new JTable();
        JScrollPane panelscroll = new JScrollPane(tablamascotas);
        panelscroll.setBorder(BorderFactory.createTitledBorder("Mascotas registradas"));
        panelprincipal.add(panelscroll, BorderLayout.CENTER);

        add(panelprincipal);
        llenarTabla();
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.addActionListener(this);
        return boton;
    }

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
        txtcodigo.setText("");
        txtdnidueno.setText("");
        txtnombre.setText("");
        cboespecie.setSelectedIndex(0);
        txtraza.setText("");
        cbosexo.setSelectedIndex(0);
        txtfecha.setText("");
        txtpeso.setText("");
        txtcodigo.requestFocus();
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación", JOptionPane.WARNING_MESSAGE);
    }

    private void grabar() {
        String codigo = txtcodigo.getText().trim();
        String dnitexto = txtdnidueno.getText().trim();
        String nombre = txtnombre.getText().trim();
        String raza = txtraza.getText().trim();
        String fecha = txtfecha.getText().trim();
        String pesotexto = txtpeso.getText().trim();

        if (codigo.isEmpty() || dnitexto.isEmpty() || nombre.isEmpty() || raza.isEmpty()
                || fecha.isEmpty() || pesotexto.isEmpty()) {
            mostrarAdvertencia("Todos los campos son obligatorios.");
            return;
        }
        if (!dnitexto.matches("\\d{8}")) {
            mostrarAdvertencia("El DNI del dueño debe tener exactamente 8 dígitos.");
            return;
        }
        if (!fecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
            mostrarAdvertencia("La fecha debe tener el formato dd/mm/aaaa.");
            return;
        }
        double peso;
        try {
            peso = Double.parseDouble(pesotexto);
        } catch (NumberFormatException ex) {
            mostrarAdvertencia("El peso debe ser un número válido.");
            return;
        }
        if (peso <= 0) {
            mostrarAdvertencia("El peso debe ser mayor que cero.");
            return;
        }
        // El dueño debe existir en el gestor de clientes
        Cliente dueno = gestorclientes.buscarPorDni(Integer.valueOf(dnitexto));
        if (dueno == null) {
            mostrarAdvertencia("No existe un cliente con ese DNI. Regístrelo primero en Clientes.");
            return;
        }
        if (gestormascotas.estaLleno()) {
            mostrarAdvertencia("No hay espacio para más mascotas.");
            return;
        }
        if (gestormascotas.buscarPorCodigo(codigo) != null) {
            mostrarAdvertencia("Ya existe una mascota con ese código.");
            return;
        }

        // Se crea el objeto de la clase hija según la especie (todos se guardan como Mascota)
        String especie = String.valueOf(cboespecie.getSelectedItem());
        String sexo = String.valueOf(cbosexo.getSelectedItem());
        Mascota nueva;
        if (especie.equals("Perro")) {
            nueva = new Perro(codigo, nombre, especie, raza, sexo, fecha, peso, "Activa", dueno);
        } else if (especie.equals("Gato")) {
            nueva = new Gato(codigo, nombre, especie, raza, sexo, fecha, peso, "Activa", dueno);
        } else {
            nueva = new Ave(codigo, nombre, especie, raza, sexo, fecha, peso, "Activa", dueno);
        }

        if (gestormascotas.registrar(nueva)) {
            JOptionPane.showMessageDialog(this, "Mascota registrada correctamente.");
            llenarTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar la mascota.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscar() {
        String codigo = txtcodigo.getText().trim();
        if (codigo.isEmpty()) {
            mostrarAdvertencia("Escriba el código de la mascota que desea buscar.");
            return;
        }
        Mascota mascota = gestormascotas.buscarPorCodigo(codigo);
        if (mascota == null) {
            mostrarAdvertencia("No existe una mascota con ese código.");
            return;
        }
        txtdnidueno.setText(String.valueOf(mascota.getCliente().getDni()));
        txtnombre.setText(mascota.getNombre());
        if (mascota instanceof Perro) {
            cboespecie.setSelectedIndex(0);
        } else if (mascota instanceof Gato) {
            cboespecie.setSelectedIndex(1);
        } else {
            cboespecie.setSelectedIndex(2);
        }
        txtraza.setText(mascota.getRaza());
        if (mascota.getSexo().equals("Macho")) {
            cbosexo.setSelectedIndex(0);
        } else {
            cbosexo.setSelectedIndex(1);
        }
        txtfecha.setText(mascota.getFechaNacimiento());
        txtpeso.setText(String.valueOf(mascota.getPeso()));
    }

    // Modificar una mascota (el código y la especie no se modifican; el dueño sí)
    private void modificar() {
        String codigo = txtcodigo.getText().trim();
        String dnitexto = txtdnidueno.getText().trim();
        String nombre = txtnombre.getText().trim();
        String raza = txtraza.getText().trim();
        String fecha = txtfecha.getText().trim();
        String pesotexto = txtpeso.getText().trim();

        if (codigo.isEmpty()) {
            mostrarAdvertencia("Escriba el código de la mascota que desea modificar.");
            return;
        }
        Mascota mascota = gestormascotas.buscarPorCodigo(codigo);
        if (mascota == null) {
            mostrarAdvertencia("No existe una mascota con ese código.");
            return;
        }
        if (dnitexto.isEmpty() || nombre.isEmpty() || raza.isEmpty() || fecha.isEmpty() || pesotexto.isEmpty()) {
            mostrarAdvertencia("Todos los campos son obligatorios.");
            return;
        }
        if (!dnitexto.matches("\\d{8}")) {
            mostrarAdvertencia("El DNI del dueño debe tener exactamente 8 dígitos.");
            return;
        }
        if (!fecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
            mostrarAdvertencia("La fecha debe tener el formato dd/mm/aaaa.");
            return;
        }
        double peso;
        try {
            peso = Double.parseDouble(pesotexto);
        } catch (NumberFormatException ex) {
            mostrarAdvertencia("El peso debe ser un número válido.");
            return;
        }
        if (peso <= 0) {
            mostrarAdvertencia("El peso debe ser mayor que cero.");
            return;
        }
        Cliente dueno = gestorclientes.buscarPorDni(Integer.valueOf(dnitexto));
        if (dueno == null) {
            mostrarAdvertencia("No existe un cliente con ese DNI.");
            return;
        }
        if (!String.valueOf(cboespecie.getSelectedItem()).equals(mascota.getEspecie())) {
            mostrarAdvertencia("La especie no se puede cambiar. Elimine la mascota y regístrela de nuevo.");
            return;
        }

        mascota.setCliente(dueno);
        mascota.setNombre(nombre);
        mascota.setRaza(raza);
        mascota.setSexo(String.valueOf(cbosexo.getSelectedItem()));
        mascota.setFechaNacimiento(fecha);
        mascota.setPeso(peso);
        JOptionPane.showMessageDialog(this, "Mascota modificada correctamente.");
        llenarTabla();
        limpiarCampos();
    }

    private void eliminar() {
        String codigo = txtcodigo.getText().trim();
        if (codigo.isEmpty()) {
            mostrarAdvertencia("Escriba el código de la mascota que desea eliminar.");
            return;
        }
        if (gestormascotas.buscarPorCodigo(codigo) == null) {
            mostrarAdvertencia("No existe una mascota con ese código.");
            return;
        }
        // TODO: pedir confirmación antes de eliminar (cuando se vea el cuadro de diálogo de confirmación)
        gestormascotas.eliminar(codigo);
        JOptionPane.showMessageDialog(this, "Mascota eliminada correctamente.");
        llenarTabla();
        limpiarCampos();
    }

    private void llenarTabla() {
        modelotabla = new DefaultTableModel();
        tablamascotas.setModel(modelotabla);
        modelotabla.addColumn("Código");
        modelotabla.addColumn("Nombre");
        modelotabla.addColumn("Especie");
        modelotabla.addColumn("Raza");
        modelotabla.addColumn("Sexo");
        modelotabla.addColumn("Nacimiento");
        modelotabla.addColumn("Peso (kg)");
        modelotabla.addColumn("Dueño");
        modelotabla.addColumn("Dosis sugerida");

        Mascota[] lista = gestormascotas.getLista();
        for (int i = 0; i < lista.length; i++) {
            Mascota mascota = lista[i];
            String[] fila = new String[9];
            fila[0] = mascota.getCodigoMascota();
            fila[1] = mascota.getNombre();
            fila[2] = mascota.getEspecie();
            fila[3] = mascota.getRaza();
            fila[4] = mascota.getSexo();
            fila[5] = mascota.getFechaNacimiento();
            fila[6] = String.valueOf(mascota.getPeso());
            fila[7] = mascota.getCliente().getNombreCompleto();
            fila[8] = String.format("%.2f", mascota.calcularDosis());
            modelotabla.addRow(fila);
        }
    }
}
