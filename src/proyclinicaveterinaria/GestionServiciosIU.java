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
public class GestionServiciosIU extends JFrame implements ActionListener {

    private GestorServicios gestorservicios;
    private DefaultTableModel modelotabla;

    private JTextField txtcodigo;
    private JTextField txtnombre;
    private JTextField txtprecio;
    private JTextField txtduracion;
    private JTable tablaservicios;

    public GestionServiciosIU(GestorServicios gestorservicios) {
        this.gestorservicios = gestorservicios;

        setTitle("Gestión de Servicios");
        setSize(800, 500);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelprincipal = new JPanel();
        panelprincipal.setLayout(new BorderLayout(10, 10));
        panelprincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelsuperior = new JPanel(new BorderLayout(10, 10));

        JLabel lbltitulo = new JLabel("GESTIÓN DE SERVICIOS", SwingConstants.CENTER);
        lbltitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lbltitulo.setForeground(new Color(0, 51, 102));
        panelsuperior.add(lbltitulo, BorderLayout.NORTH);

        JPanel panelformulario = new JPanel(new GridLayout(2, 4, 10, 10));
        panelformulario.add(new JLabel("Código:"));
        txtcodigo = new JTextField();
        panelformulario.add(txtcodigo);
        panelformulario.add(new JLabel("Nombre:"));
        txtnombre = new JTextField();
        panelformulario.add(txtnombre);
        panelformulario.add(new JLabel("Precio base (con punto):"));
        txtprecio = new JTextField();
        panelformulario.add(txtprecio);
        panelformulario.add(new JLabel("Duración (minutos):"));
        txtduracion = new JTextField();
        panelformulario.add(txtduracion);
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

        tablaservicios = new JTable();
        JScrollPane panelscroll = new JScrollPane(tablaservicios);
        panelscroll.setBorder(BorderFactory.createTitledBorder("Servicios registrados"));
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
        txtnombre.setText("");
        txtprecio.setText("");
        txtduracion.setText("");
        txtcodigo.requestFocus();
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación", JOptionPane.WARNING_MESSAGE);
    }

    private void grabar() {
        String codigo = txtcodigo.getText().trim();
        String nombre = txtnombre.getText().trim();
        String preciotexto = txtprecio.getText().trim();
        String duraciontexto = txtduracion.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || preciotexto.isEmpty() || duraciontexto.isEmpty()) {
            mostrarAdvertencia("Todos los campos son obligatorios.");
            return;
        }
        // El precio y la duración deben ser números (try/catch como en las clases de excepciones)
        double precio;
        int duracion;
        try {
            precio = Double.parseDouble(preciotexto);
            duracion = Integer.valueOf(duraciontexto);
        } catch (NumberFormatException ex) {
            mostrarAdvertencia("El precio y la duración deben ser números válidos.");
            return;
        }
        if (precio <= 0 || duracion <= 0) {
            mostrarAdvertencia("El precio y la duración deben ser mayores que cero.");
            return;
        }
        if (gestorservicios.estaLleno()) {
            mostrarAdvertencia("No hay espacio para más servicios.");
            return;
        }
        if (gestorservicios.buscarPorCodigo(codigo) != null) {
            mostrarAdvertencia("Ya existe un servicio con ese código.");
            return;
        }

        Servicio nuevo = new Servicio(codigo, nombre, precio, duracion);
        if (gestorservicios.registrar(nuevo)) {
            JOptionPane.showMessageDialog(this, "Servicio registrado correctamente.");
            llenarTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el servicio.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscar() {
        String codigo = txtcodigo.getText().trim();
        if (codigo.isEmpty()) {
            mostrarAdvertencia("Escriba el código del servicio que desea buscar.");
            return;
        }
        Servicio servicio = gestorservicios.buscarPorCodigo(codigo);
        if (servicio == null) {
            mostrarAdvertencia("No existe un servicio con ese código.");
            return;
        }
        txtnombre.setText(servicio.getNombre());
        txtprecio.setText(String.valueOf(servicio.getPreciobase()));
        txtduracion.setText(String.valueOf(servicio.getDuracion()));
    }

    // Modificar un servicio (el código no se modifica)
    private void modificar() {
        String codigo = txtcodigo.getText().trim();
        String nombre = txtnombre.getText().trim();
        String preciotexto = txtprecio.getText().trim();
        String duraciontexto = txtduracion.getText().trim();

        if (codigo.isEmpty()) {
            mostrarAdvertencia("Escriba el código del servicio que desea modificar.");
            return;
        }
        Servicio servicio = gestorservicios.buscarPorCodigo(codigo);
        if (servicio == null) {
            mostrarAdvertencia("No existe un servicio con ese código.");
            return;
        }
        if (nombre.isEmpty() || preciotexto.isEmpty() || duraciontexto.isEmpty()) {
            mostrarAdvertencia("Todos los campos son obligatorios.");
            return;
        }
        double precio;
        int duracion;
        try {
            precio = Double.parseDouble(preciotexto);
            duracion = Integer.valueOf(duraciontexto);
        } catch (NumberFormatException ex) {
            mostrarAdvertencia("El precio y la duración deben ser números válidos.");
            return;
        }
        if (precio <= 0 || duracion <= 0) {
            mostrarAdvertencia("El precio y la duración deben ser mayores que cero.");
            return;
        }

        servicio.setNombre(nombre);
        servicio.setPreciobase(precio);
        servicio.setDuracion(duracion);
        JOptionPane.showMessageDialog(this, "Servicio modificado correctamente.");
        llenarTabla();
        limpiarCampos();
    }

    private void eliminar() {
        String codigo = txtcodigo.getText().trim();
        if (codigo.isEmpty()) {
            mostrarAdvertencia("Escriba el código del servicio que desea eliminar.");
            return;
        }
        if (gestorservicios.buscarPorCodigo(codigo) == null) {
            mostrarAdvertencia("No existe un servicio con ese código.");
            return;
        }
        // TODO: pedir confirmación antes de eliminar (cuando se vea el cuadro de diálogo de confirmación)
        gestorservicios.eliminar(codigo);
        JOptionPane.showMessageDialog(this, "Servicio eliminado correctamente.");
        llenarTabla();
        limpiarCampos();
    }

    private void llenarTabla() {
        modelotabla = new DefaultTableModel();
        tablaservicios.setModel(modelotabla);
        modelotabla.addColumn("Código");
        modelotabla.addColumn("Nombre");
        modelotabla.addColumn("Precio base (S/)");
        modelotabla.addColumn("Duración (min)");

        Servicio[] lista = gestorservicios.getLista();
        for (int i = 0; i < lista.length; i++) {
            Servicio servicio = lista[i];
            String[] fila = new String[4];
            fila[0] = servicio.getCodigo();
            fila[1] = servicio.getNombre();
            fila[2] = String.format("%.2f", servicio.getPreciobase());
            fila[3] = String.valueOf(servicio.getDuracion());
            modelotabla.addRow(fila);
        }
    }
}
