package proyclinicaveterinaria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 * Atención veterinaria (RF06): ventana del veterinario. Solo ve y atiende SUS citas.
 * Flujo: Iniciar atención -> registrar diagnóstico, tratamiento y medicamentos -> Finalizar (o Cancelar).
 * Una atención finalizada ya no se puede modificar.
 */
public class GestionAtencionesIU extends JFrame implements ActionListener {

    private GestorAtenciones gestoratenciones;
    private GestorCitas gestorcitas;
    private GestorMedicamentos gestormedicamentos;
    private Empleado veterinarioactual; // el veterinario que inició sesión
    private DefaultTableModel modelotabla;

    private JTextField txtnumero;
    private JTextField txtdiagnostico;
    private JTextField txttratamiento;
    private JTextField txtcodigomedicamento;
    private JTextField txtunidades;
    private JTable tablaatenciones;

    public GestionAtencionesIU(GestorAtenciones gestoratenciones, GestorCitas gestorcitas,
            GestorMedicamentos gestormedicamentos, Empleado veterinarioactual) {
        this.gestoratenciones = gestoratenciones;
        this.gestorcitas = gestorcitas;
        this.gestormedicamentos = gestormedicamentos;
        this.veterinarioactual = veterinarioactual;

        setTitle("Atención Veterinaria");
        setSize(1100, 600);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelprincipal = new JPanel();
        panelprincipal.setLayout(new BorderLayout(10, 10));
        panelprincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelsuperior = new JPanel(new BorderLayout(10, 10));

        JLabel lbltitulo = new JLabel("ATENCIÓN VETERINARIA - " + veterinarioactual.getNombreCompleto(), SwingConstants.CENTER);
        lbltitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lbltitulo.setForeground(new Color(0, 51, 102));
        panelsuperior.add(lbltitulo, BorderLayout.NORTH);

        JPanel panelformulario = new JPanel(new GridLayout(3, 4, 10, 10));
        panelformulario.add(new JLabel("N° de cita:"));
        txtnumero = new JTextField();
        panelformulario.add(txtnumero);
        panelformulario.add(new JLabel("Código del medicamento:"));
        txtcodigomedicamento = new JTextField();
        panelformulario.add(txtcodigomedicamento);
        panelformulario.add(new JLabel("Diagnóstico:"));
        txtdiagnostico = new JTextField();
        panelformulario.add(txtdiagnostico);
        panelformulario.add(new JLabel("Unidades del medicamento:"));
        txtunidades = new JTextField();
        panelformulario.add(txtunidades);
        panelformulario.add(new JLabel("Tratamiento:"));
        txttratamiento = new JTextField();
        panelformulario.add(txttratamiento);
        panelformulario.add(new JLabel(""));
        panelformulario.add(new JLabel(""));
        panelsuperior.add(panelformulario, BorderLayout.CENTER);

        JPanel panelbotones = new JPanel(new GridLayout(2, 4, 10, 10));
        panelbotones.add(crearBoton("Buscar"));
        panelbotones.add(crearBoton("Iniciar atención"));
        panelbotones.add(crearBoton("Guardar datos"));
        panelbotones.add(crearBoton("Agregar medicamento"));
        panelbotones.add(crearBoton("Finalizar"));
        panelbotones.add(crearBoton("Cancelar atención"));
        panelbotones.add(crearBoton("Ver historial"));
        panelbotones.add(crearBoton("Cerrar"));
        panelsuperior.add(panelbotones, BorderLayout.SOUTH);

        panelprincipal.add(panelsuperior, BorderLayout.NORTH);

        tablaatenciones = new JTable();
        JScrollPane panelscroll = new JScrollPane(tablaatenciones);
        panelscroll.setBorder(BorderFactory.createTitledBorder("Mis citas"));
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
        if (comando.equals("Buscar")) {
            buscar();
        } else if (comando.equals("Iniciar atención")) {
            iniciar();
        } else if (comando.equals("Guardar datos")) {
            guardar();
        } else if (comando.equals("Agregar medicamento")) {
            agregarMedicamento();
        } else if (comando.equals("Finalizar")) {
            finalizar();
        } else if (comando.equals("Cancelar atención")) {
            cancelar();
        } else if (comando.equals("Ver historial")) {
            verHistorial();
        } else if (comando.equals("Cerrar")) {
            dispose();
        }
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación", JOptionPane.WARNING_MESSAGE);
    }

    // Devuelve la cita del N° escrito si es de este veterinario; si no, muestra el error y devuelve null
    private Cita buscarCitaPropia() {
        String numerotexto = txtnumero.getText().trim();
        if (!numerotexto.matches("\\d+")) {
            mostrarAdvertencia("Escriba el N° de la cita (solo dígitos).");
            return null;
        }
        Cita cita = gestorcitas.buscarPorNumero(Integer.valueOf(numerotexto));
        if (cita == null) {
            mostrarAdvertencia("No existe una cita con ese número.");
            return null;
        }
        if (cita.getVeterinario() != veterinarioactual) {
            mostrarAdvertencia("Esa cita está asignada a otro veterinario.");
            return null;
        }
        return cita;
    }

    // Devuelve la atención solo si la cita está "En Atención" (única etapa en la que se puede modificar)
    private AtencionVeterinaria buscarAtencionActiva(Cita cita) {
        if (cita.getEstado().equals("Finalizada")) {
            mostrarAdvertencia("La atención ya fue finalizada y no se puede modificar.");
            return null;
        }
        if (!cita.getEstado().equals("En Atención")) {
            mostrarAdvertencia("La cita está en estado " + cita.getEstado() + ". Primero debe iniciar la atención.");
            return null;
        }
        return gestoratenciones.buscarPorNumeroCita(cita.getNumero());
    }

    private void buscar() {
        Cita cita = buscarCitaPropia();
        if (cita == null) {
            return;
        }
        AtencionVeterinaria atencion = gestoratenciones.buscarPorNumeroCita(cita.getNumero());
        if (atencion == null) {
            txtdiagnostico.setText("");
            txttratamiento.setText("");
        } else {
            txtdiagnostico.setText(atencion.getDiagnostico());
            txttratamiento.setText(atencion.getTratamiento());
        }
    }

    private void iniciar() {
        Cita cita = buscarCitaPropia();
        if (cita == null) {
            return;
        }
        if (!cita.getEstado().equals("Programada")) {
            mostrarAdvertencia("Solo se puede iniciar una cita en estado Programada (esta está " + cita.getEstado() + ").");
            return;
        }
        if (gestoratenciones.estaLleno()) {
            mostrarAdvertencia("No hay espacio para más atenciones.");
            return;
        }
        // TODO: comprobar que la fecha de la cita sea la de hoy (requiere trabajar con la fecha actual)
        gestoratenciones.registrar(new AtencionVeterinaria(cita));
        cita.cambiarEstado("En Atención");
        JOptionPane.showMessageDialog(this, "Atención iniciada. Registre el diagnóstico, el tratamiento y los medicamentos.");
        llenarTabla();
    }

    private void guardar() {
        Cita cita = buscarCitaPropia();
        if (cita == null) {
            return;
        }
        AtencionVeterinaria atencion = buscarAtencionActiva(cita);
        if (atencion == null) {
            return;
        }
        atencion.setDiagnostico(txtdiagnostico.getText().trim());
        atencion.setTratamiento(txttratamiento.getText().trim());
        JOptionPane.showMessageDialog(this, "Diagnóstico y tratamiento guardados.");
        llenarTabla();
    }

    private void agregarMedicamento() {
        Cita cita = buscarCitaPropia();
        if (cita == null) {
            return;
        }
        AtencionVeterinaria atencion = buscarAtencionActiva(cita);
        if (atencion == null) {
            return;
        }
        String codigo = txtcodigomedicamento.getText().trim();
        String unidadestexto = txtunidades.getText().trim();
        if (codigo.isEmpty() || unidadestexto.isEmpty()) {
            mostrarAdvertencia("Escriba el código del medicamento y las unidades.");
            return;
        }
        Medicamento medicamento = gestormedicamentos.buscarPorCodigo(codigo);
        if (medicamento == null) {
            mostrarAdvertencia("No existe un medicamento con ese código.");
            return;
        }
        int unidades;
        try {
            unidades = Integer.valueOf(unidadestexto);
        } catch (NumberFormatException ex) {
            mostrarAdvertencia("Las unidades deben ser un número entero.");
            return;
        }
        // RF07: el medicamento no debe estar vencido (a la fecha de la cita) y debe alcanzar el stock
        String error = medicamento.validarUso(unidades, cita.getFecha());
        if (!error.equals("")) {
            mostrarAdvertencia(error);
            return;
        }
        if (!atencion.agregarMedicamento(medicamento, unidades)) {
            mostrarAdvertencia("No se pueden agregar más medicamentos distintos a esta atención.");
            return;
        }
        // Solo si todo salió bien se descuenta del inventario
        medicamento.descontarStock(unidades);
        JOptionPane.showMessageDialog(this, "Medicamento agregado. Stock restante de " + medicamento.getNombre() + ": " + medicamento.getStock());
        txtcodigomedicamento.setText("");
        txtunidades.setText("");
        llenarTabla();
    }

    private void finalizar() {
        Cita cita = buscarCitaPropia();
        if (cita == null) {
            return;
        }
        AtencionVeterinaria atencion = buscarAtencionActiva(cita);
        if (atencion == null) {
            return;
        }
        // Se toman los textos que están en el formulario
        String diagnostico = txtdiagnostico.getText().trim();
        String tratamiento = txttratamiento.getText().trim();
        if (diagnostico.isEmpty() || tratamiento.isEmpty()) {
            mostrarAdvertencia("Para finalizar debe escribir el diagnóstico y el tratamiento.");
            return;
        }
        atencion.setDiagnostico(diagnostico);
        atencion.setTratamiento(tratamiento);
        // TODO: pedir confirmación antes de finalizar (cuando se vea el cuadro de diálogo de confirmación)
        cita.cambiarEstado("Finalizada");

        // RF09: suma una atención finalizada al cliente (dueño de la mascota)
        Cliente cliente = cita.getMascota().getCliente();
        cliente.sumarAtencionFinalizada();
        JOptionPane.showMessageDialog(this, "Atención finalizada.\nCliente: " + cliente.getNombreCompleto()
                + "\nAtenciones finalizadas: " + cliente.getAtencionesFinalizadas()
                + "\nCategoría: " + cliente.getDescuento().getNombre());
        // TODO RF08: aquí se generará el comprobante (boleta o factura) de la atención
        txtdiagnostico.setText("");
        txttratamiento.setText("");
        llenarTabla();
    }

    private void cancelar() {
        Cita cita = buscarCitaPropia();
        if (cita == null) {
            return;
        }
        if (cita.getEstado().equals("Programada")) {
            cita.cambiarEstado("Cancelada");
        } else if (cita.getEstado().equals("En Atención")) {
            // Se devuelve al inventario lo que se había descontado
            AtencionVeterinaria atencion = gestoratenciones.buscarPorNumeroCita(cita.getNumero());
            atencion.devolverStock();
            cita.cambiarEstado("Cancelada");
        } else {
            mostrarAdvertencia("No se puede cancelar una cita en estado " + cita.getEstado() + ".");
            return;
        }
        // TODO: pedir confirmación antes de cancelar (cuando se vea el cuadro de diálogo de confirmación)
        JOptionPane.showMessageDialog(this, "Cita cancelada correctamente.");
        llenarTabla();
    }

    private void verHistorial() {
        Cita cita = buscarCitaPropia();
        if (cita == null) {
            return;
        }
        JOptionPane.showMessageDialog(this, "Historial de la cita " + cita.getNumero() + ":\n" + cita.getTextoHistorial());
    }

    // Muestra solo las citas de este veterinario
    private void llenarTabla() {
        modelotabla = new DefaultTableModel();
        tablaatenciones.setModel(modelotabla);
        modelotabla.addColumn("N°");
        modelotabla.addColumn("Fecha");
        modelotabla.addColumn("Hora");
        modelotabla.addColumn("Mascota");
        modelotabla.addColumn("Servicio");
        modelotabla.addColumn("Estado");
        modelotabla.addColumn("Diagnóstico");
        modelotabla.addColumn("Medicamentos usados");

        Cita[] lista = gestorcitas.getLista();
        for (int i = 0; i < lista.length; i++) {
            Cita cita = lista[i];
            if (cita.getVeterinario() == veterinarioactual) {
                AtencionVeterinaria atencion = gestoratenciones.buscarPorNumeroCita(cita.getNumero());
                String[] fila = new String[8];
                fila[0] = String.valueOf(cita.getNumero());
                fila[1] = cita.getFecha();
                fila[2] = cita.getHora();
                fila[3] = cita.getMascota().getCodigoMascota() + " - " + cita.getMascota().getNombre();
                fila[4] = cita.getServicio().getNombre();
                fila[5] = cita.getEstado();
                if (atencion == null) {
                    fila[6] = "-";
                    fila[7] = "-";
                } else {
                    fila[6] = atencion.getDiagnostico();
                    fila[7] = atencion.getTextoMedicamentos();
                }
                modelotabla.addRow(fila);
            }
        }
    }
}