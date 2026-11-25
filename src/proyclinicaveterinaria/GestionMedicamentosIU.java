package proyclinicaveterinaria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 * Gestión de medicamentos e inventario (RF07): registrar, buscar, modificar y eliminar, con tabla de consulta.
 * La validación de vencimiento y de stock se hace al USAR el medicamento en una atención (RF06),
 * con Medicamento.validarUso(), porque ahí se conoce la fecha de la atención.
 */
public class GestionMedicamentosIU extends JFrame implements ActionListener {

    private GestorMedicamentos gestormedicamentos;
    private Validaciones validaciones = new Validaciones();
    private DefaultTableModel modelotabla;

    private JTextField txtcodigo;
    private JTextField txtnombre;
    private JTextField txtstock;
    private JTextField txtprecio;
    private JTextField txtvencimiento;
    private JTable tablamedicamentos;

    public GestionMedicamentosIU(GestorMedicamentos gestormedicamentos) {
        this.gestormedicamentos = gestormedicamentos;

        setTitle("Gestión de Medicamentos");
        setSize(900, 520);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelprincipal = new JPanel();
        panelprincipal.setLayout(new BorderLayout(10, 10));
        panelprincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelsuperior = new JPanel(new BorderLayout(10, 10));

        JLabel lbltitulo = new JLabel("GESTIÓN DE MEDICAMENTOS E INVENTARIO", SwingConstants.CENTER);
        lbltitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lbltitulo.setForeground(new Color(0, 51, 102));
        panelsuperior.add(lbltitulo, BorderLayout.NORTH);

        JPanel panelformulario = new JPanel(new GridLayout(3, 4, 10, 10));
        panelformulario.add(new JLabel("Código:"));
        txtcodigo = new JTextField();
        panelformulario.add(txtcodigo);
        panelformulario.add(new JLabel("Nombre:"));
        txtnombre = new JTextField();
        panelformulario.add(txtnombre);
        panelformulario.add(new JLabel("Stock (unidades):"));
        txtstock = new JTextField();
        panelformulario.add(txtstock);
        panelformulario.add(new JLabel("Precio unitario (con punto):"));
        txtprecio = new JTextField();
        panelformulario.add(txtprecio);
        panelformulario.add(new JLabel("Vencimiento (dd/mm/aaaa):"));
        txtvencimiento = new JTextField();
        panelformulario.add(txtvencimiento);
        panelformulario.add(new JLabel(""));
        panelformulario.add(new JLabel(""));
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

        tablamedicamentos = new JTable();
        JScrollPane panelscroll = new JScrollPane(tablamedicamentos);
        panelscroll.setBorder(BorderFactory.createTitledBorder("Inventario de medicamentos"));
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
        txtstock.setText("");
        txtprecio.setText("");
        txtvencimiento.setText("");
        txtcodigo.requestFocus();
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación", JOptionPane.WARNING_MESSAGE);
    }

    private void grabar() {
        String codigo = txtcodigo.getText().trim();
        String nombre = txtnombre.getText().trim();
        String stocktexto = txtstock.getText().trim();
        String preciotexto = txtprecio.getText().trim();
        String vencimiento = txtvencimiento.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || stocktexto.isEmpty() || preciotexto.isEmpty() || vencimiento.isEmpty()) {
            mostrarAdvertencia("Todos los campos son obligatorios.");
            return;
        }
        int stock;
        double precio;
        try {
            stock = Integer.valueOf(stocktexto);
            precio = Double.parseDouble(preciotexto);
        } catch (NumberFormatException ex) {
            mostrarAdvertencia("El stock y el precio deben ser números válidos.");
            return;
        }
        if (stock < 0 || precio <= 0) {
            mostrarAdvertencia("El stock no puede ser negativo y el precio debe ser mayor que cero.");
            return;
        }
        if (!validaciones.esFechaValida(vencimiento)) {
            mostrarAdvertencia("La fecha de vencimiento no es válida. Use el formato dd/mm/aaaa.");
            return;
        }
        if (gestormedicamentos.estaLleno()) {
            mostrarAdvertencia("No hay espacio para más medicamentos.");
            return;
        }
        if (gestormedicamentos.buscarPorCodigo(codigo) != null) {
            mostrarAdvertencia("Ya existe un medicamento con ese código.");
            return;
        }

        Medicamento nuevo = new Medicamento(codigo, nombre, stock, precio, vencimiento);
        if (gestormedicamentos.registrar(nuevo)) {
            JOptionPane.showMessageDialog(this, "Medicamento registrado correctamente.");
            llenarTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el medicamento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscar() {
        String codigo = txtcodigo.getText().trim();
        if (codigo.isEmpty()) {
            mostrarAdvertencia("Escriba el código del medicamento que desea buscar.");
            return;
        }
        Medicamento medicamento = gestormedicamentos.buscarPorCodigo(codigo);
        if (medicamento == null) {
            mostrarAdvertencia("No existe un medicamento con ese código.");
            return;
        }
        txtnombre.setText(medicamento.getNombre());
        txtstock.setText(String.valueOf(medicamento.getStock()));
        txtprecio.setText(String.valueOf(medicamento.getPreciounitario()));
        txtvencimiento.setText(medicamento.getFechavencimiento());
    }

    // Modificar un medicamento (el código no se modifica)
    private void modificar() {
        String codigo = txtcodigo.getText().trim();
        String nombre = txtnombre.getText().trim();
        String stocktexto = txtstock.getText().trim();
        String preciotexto = txtprecio.getText().trim();
        String vencimiento = txtvencimiento.getText().trim();

        if (codigo.isEmpty()) {
            mostrarAdvertencia("Escriba el código del medicamento que desea modificar.");
            return;
        }
        Medicamento medicamento = gestormedicamentos.buscarPorCodigo(codigo);
        if (medicamento == null) {
            mostrarAdvertencia("No existe un medicamento con ese código.");
            return;
        }
        if (nombre.isEmpty() || stocktexto.isEmpty() || preciotexto.isEmpty() || vencimiento.isEmpty()) {
            mostrarAdvertencia("Todos los campos son obligatorios.");
            return;
        }
        int stock;
        double precio;
        try {
            stock = Integer.valueOf(stocktexto);
            precio = Double.parseDouble(preciotexto);
        } catch (NumberFormatException ex) {
            mostrarAdvertencia("El stock y el precio deben ser números válidos.");
            return;
        }
        if (stock < 0 || precio <= 0) {
            mostrarAdvertencia("El stock no puede ser negativo y el precio debe ser mayor que cero.");
            return;
        }
        if (!validaciones.esFechaValida(vencimiento)) {
            mostrarAdvertencia("La fecha de vencimiento no es válida. Use el formato dd/mm/aaaa.");
            return;
        }

        medicamento.setNombre(nombre);
        medicamento.setStock(stock);
        medicamento.setPreciounitario(precio);
        medicamento.setFechavencimiento(vencimiento);
        JOptionPane.showMessageDialog(this, "Medicamento modificado correctamente.");
        llenarTabla();
        limpiarCampos();
    }

    private void eliminar() {
        String codigo = txtcodigo.getText().trim();
        if (codigo.isEmpty()) {
            mostrarAdvertencia("Escriba el código del medicamento que desea eliminar.");
            return;
        }
        if (gestormedicamentos.buscarPorCodigo(codigo) == null) {
            mostrarAdvertencia("No existe un medicamento con ese código.");
            return;
        }
        // TODO: pedir confirmación antes de eliminar (cuando se vea el cuadro de diálogo de confirmación)
        gestormedicamentos.eliminar(codigo);
        JOptionPane.showMessageDialog(this, "Medicamento eliminado correctamente.");
        llenarTabla();
        limpiarCampos();
    }

    private void llenarTabla() {
        modelotabla = new DefaultTableModel();
        tablamedicamentos.setModel(modelotabla);
        modelotabla.addColumn("Código");
        modelotabla.addColumn("Nombre");
        modelotabla.addColumn("Stock");
        modelotabla.addColumn("Precio unitario (S/)");
        modelotabla.addColumn("Vencimiento");

        Medicamento[] lista = gestormedicamentos.getLista();
        for (int i = 0; i < lista.length; i++) {
            Medicamento medicamento = lista[i];
            String[] fila = new String[5];
            fila[0] = medicamento.getCodigo();
            fila[1] = medicamento.getNombre();
            fila[2] = String.valueOf(medicamento.getStock());
            fila[3] = String.format("%.2f", medicamento.getPreciounitario());
            fila[4] = medicamento.getFechavencimiento();
            modelotabla.addRow(fila);
        }
    }
}