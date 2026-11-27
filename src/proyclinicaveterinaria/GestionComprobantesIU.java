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
 * @author josec
 */
public class GestionComprobantesIU extends JFrame implements ActionListener {

    private GestorComprobantes gestorcomprobantes;
    private DefaultTableModel modelotabla;

    private JTextField txtnumero;
    private JTable tablacomprobantes;

    public GestionComprobantesIU(GestorComprobantes gestorcomprobantes) {
        this.gestorcomprobantes = gestorcomprobantes;

        setTitle("Comprobantes emitidos");
        setSize(1000, 500);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelprincipal = new JPanel();
        panelprincipal.setLayout(new BorderLayout(10, 10));
        panelprincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelsuperior = new JPanel(new BorderLayout(10, 10));

        JLabel lbltitulo = new JLabel("COMPROBANTES EMITIDOS (BOLETAS Y FACTURAS)", SwingConstants.CENTER);
        lbltitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lbltitulo.setForeground(new Color(0, 51, 102));
        panelsuperior.add(lbltitulo, BorderLayout.NORTH);

        JPanel panelformulario = new JPanel(new GridLayout(1, 2, 10, 10));
        panelformulario.add(new JLabel("N° de comprobante (para ver el detalle):"));
        txtnumero = new JTextField();
        panelformulario.add(txtnumero);
        panelsuperior.add(panelformulario, BorderLayout.CENTER);

        JPanel panelbotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelbotones.add(crearBoton("Ver detalle"));
        panelbotones.add(crearBoton("Cerrar"));
        panelsuperior.add(panelbotones, BorderLayout.SOUTH);

        panelprincipal.add(panelsuperior, BorderLayout.NORTH);

        tablacomprobantes = new JTable();
        JScrollPane panelscroll = new JScrollPane(tablacomprobantes);
        panelscroll.setBorder(BorderFactory.createTitledBorder("Comprobantes"));
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
        if (comando.equals("Ver detalle")) {
            verDetalle();
        } else if (comando.equals("Cerrar")) {
            dispose();
        }
    }

    private void verDetalle() {
        String numerotexto = txtnumero.getText().trim();
        if (!numerotexto.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Escriba el N° del comprobante (solo dígitos).", "Error de validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Comprobante comprobante = gestorcomprobantes.buscarPorNumero(Integer.valueOf(numerotexto));
        if (comprobante == null) {
            JOptionPane.showMessageDialog(this, "No existe un comprobante con ese número.", "Error de validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, comprobante.getTextoComprobante());
    }

    private void llenarTabla() {
        modelotabla = new DefaultTableModel();
        tablacomprobantes.setModel(modelotabla);
        modelotabla.addColumn("N°");
        modelotabla.addColumn("Comprobante");
        modelotabla.addColumn("Tipo");
        modelotabla.addColumn("Fecha");
        modelotabla.addColumn("Cliente");
        modelotabla.addColumn("Subtotal (S/)");
        modelotabla.addColumn("Descuento (S/)");
        modelotabla.addColumn("IGV (S/)");
        modelotabla.addColumn("Total (S/)");

        Comprobante[] lista = gestorcomprobantes.getLista();
        for (int i = 0; i < lista.length; i++) {
            Comprobante comprobante = lista[i];
            String[] fila = new String[9];
            fila[0] = String.valueOf(comprobante.getNumero());
            fila[1] = comprobante.getSerie();
            fila[2] = comprobante.getTipo();
            fila[3] = comprobante.getFecha();
            fila[4] = comprobante.getCliente().getNombreCompleto();
            fila[5] = String.format("%.2f", comprobante.getSubtotal());
            fila[6] = String.format("%.2f", comprobante.getDescuento());
            fila[7] = String.format("%.2f", comprobante.getIgv());
            fila[8] = String.format("%.2f", comprobante.getTotal());
            modelotabla.addRow(fila);
        }
    }
}
