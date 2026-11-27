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
public class ReportesIU extends JFrame implements ActionListener {

    private Reporte[] reportes;
    private DefaultTableModel modelotabla;

    private JComboBox cboreporte;
    private JTable tablareporte;
    private JScrollPane panelscroll;

    public ReportesIU(GestorComprobantes gestorcomprobantes, GestorServicios gestorservicios,
            GestorEmpleados gestorempleados, GestorCitas gestorcitas, GestorClientes gestorclientes) {

        // Los cuatro reportes del sistema, todos guardados como Reporte
        reportes = new Reporte[4];
        reportes[0] = new ReporteIngresos(gestorcomprobantes);
        reportes[1] = new ReporteServicios(gestorservicios, gestorcomprobantes);
        reportes[2] = new ReporteVeterinarios(gestorempleados, gestorcitas);
        reportes[3] = new ReporteClientesFrecuentes(gestorclientes);

        String[] nombres = new String[reportes.length];
        for (int i = 0; i < reportes.length; i++) {
            nombres[i] = reportes[i].getNombre();
        }

        setTitle("Reportes");
        setSize(950, 480);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelprincipal = new JPanel();
        panelprincipal.setLayout(new BorderLayout(10, 10));
        panelprincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelsuperior = new JPanel(new BorderLayout(10, 10));

        JLabel lbltitulo = new JLabel("REPORTES", SwingConstants.CENTER);
        lbltitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lbltitulo.setForeground(new Color(0, 51, 102));
        panelsuperior.add(lbltitulo, BorderLayout.NORTH);

        JPanel panelformulario = new JPanel(new GridLayout(1, 2, 10, 10));
        panelformulario.add(new JLabel("Reporte:"));
        cboreporte = new JComboBox();
        cboreporte.setModel(new DefaultComboBoxModel(nombres));
        panelformulario.add(cboreporte);
        panelsuperior.add(panelformulario, BorderLayout.CENTER);

        JPanel panelbotones = new JPanel(new GridLayout(1, 2, 10, 10));
        panelbotones.add(crearBoton("Generar"));
        panelbotones.add(crearBoton("Cerrar"));
        panelsuperior.add(panelbotones, BorderLayout.SOUTH);

        panelprincipal.add(panelsuperior, BorderLayout.NORTH);

        tablareporte = new JTable();
        panelscroll = new JScrollPane(tablareporte);
        panelscroll.setBorder(BorderFactory.createTitledBorder("Resultado"));
        panelprincipal.add(panelscroll, BorderLayout.CENTER);

        add(panelprincipal);
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
        if (comando.equals("Generar")) {
            generar();
        } else if (comando.equals("Cerrar")) {
            dispose();
        }
    }

    private void generar() {
        // Se busca el reporte que coincide con el nombre elegido en la lista
        String elegido = String.valueOf(cboreporte.getSelectedItem());
        Reporte reporte = null;
        for (int i = 0; i < reportes.length; i++) {
            if (reportes[i].getNombre().equals(elegido)) {
                reporte = reportes[i];
            }
        }

        // Da igual qué reporte sea: todos tienen getColumnas() y generar()
        String[] columnas = reporte.getColumnas();
        String[][] filas = reporte.generar();

        modelotabla = new DefaultTableModel();
        tablareporte.setModel(modelotabla);
        for (int i = 0; i < columnas.length; i++) {
            modelotabla.addColumn(columnas[i]);
        }
        for (int i = 0; i < filas.length; i++) {
            modelotabla.addRow(filas[i]);
        }
        panelscroll.setBorder(BorderFactory.createTitledBorder("Reporte: " + reporte.getNombre()));

        if (filas.length == 0) {
            JOptionPane.showMessageDialog(this, "Todavía no hay datos para este reporte.");
        }
    }
}
