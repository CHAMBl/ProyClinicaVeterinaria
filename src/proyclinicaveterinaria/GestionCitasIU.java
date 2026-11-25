package proyclinicaveterinaria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 * Programación de citas (RF05): programar, buscar, modificar y cancelar, con tabla de consulta.
 * Valida: mascota registrada, veterinario disponible, horario de atención y que no se cruce
 * con otra cita del mismo veterinario.
 */
public class GestionCitasIU extends JFrame implements ActionListener {

    private GestorCitas gestorcitas;
    private GestorMascotas gestormascotas;
    private Validaciones validaciones = new Validaciones();
    private DefaultTableModel modelotabla;

    // Horario de atención de la clínica, en minutos desde la medianoche (08:00 a 18:00)
    private int aperturaminutos = 8 * 60;
    private int cierreminutos = 18 * 60;

    // Veterinarios y servicios que había al abrir la ventana, con el texto que se muestra en cada combo
    private Empleado[] veterinarios;
    private String[] textoveterinarios;
    private Servicio[] servicios;
    private String[] textoservicios;

    private JTextField txtnumero;
    private JTextField txtcodigomascota;
    private JComboBox cboveterinario;
    private JComboBox cboservicio;
    private JTextField txtfecha;
    private JTextField txthora;
    private JTable tablacitas;

    public GestionCitasIU(GestorCitas gestorcitas, GestorMascotas gestormascotas,
            GestorEmpleados gestorempleados, GestorServicios gestorservicios) {
        this.gestorcitas = gestorcitas;
        this.gestormascotas = gestormascotas;

        // Armar la lista de veterinarios: se cuentan primero y luego se copian a un arreglo
        Empleado[] empleados = gestorempleados.getLista();
        int cantidadvets = 0;
        for (int i = 0; i < empleados.length; i++) {
            if (empleados[i] instanceof Veterinario) {
                cantidadvets++;
            }
        }
        veterinarios = new Empleado[cantidadvets];
        textoveterinarios = new String[cantidadvets];
        int posicion = 0;
        for (int i = 0; i < empleados.length; i++) {
            if (empleados[i] instanceof Veterinario) {
                veterinarios[posicion] = empleados[i];
                textoveterinarios[posicion] = empleados[i].getDni() + " - " + empleados[i].getNombreCompleto();
                posicion++;
            }
        }

        // Armar la lista de servicios
        servicios = gestorservicios.getLista();
        textoservicios = new String[servicios.length];
        for (int i = 0; i < servicios.length; i++) {
            textoservicios[i] = servicios[i].getCodigo() + " - " + servicios[i].getNombre();
        }

        setTitle("Programación de Citas");
        setSize(1000, 560);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelprincipal = new JPanel();
        panelprincipal.setLayout(new BorderLayout(10, 10));
        panelprincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelsuperior = new JPanel(new BorderLayout(10, 10));

        JLabel lbltitulo = new JLabel("PROGRAMACIÓN DE CITAS (horario de atención: 08:00 a 18:00)", SwingConstants.CENTER);
        lbltitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lbltitulo.setForeground(new Color(0, 51, 102));
        panelsuperior.add(lbltitulo, BorderLayout.NORTH);

        JPanel panelformulario = new JPanel(new GridLayout(3, 4, 10, 10));
        panelformulario.add(new JLabel("N° de cita (solo para buscar/modificar):"));
        txtnumero = new JTextField();
        panelformulario.add(txtnumero);
        panelformulario.add(new JLabel("Código de la mascota:"));
        txtcodigomascota = new JTextField();
        panelformulario.add(txtcodigomascota);
        panelformulario.add(new JLabel("Veterinario:"));
        cboveterinario = new JComboBox();
        cboveterinario.setModel(new DefaultComboBoxModel(textoveterinarios));
        panelformulario.add(cboveterinario);
        panelformulario.add(new JLabel("Servicio:"));
        cboservicio = new JComboBox();
        cboservicio.setModel(new DefaultComboBoxModel(textoservicios));
        panelformulario.add(cboservicio);
        panelformulario.add(new JLabel("Fecha (dd/mm/aaaa):"));
        txtfecha = new JTextField();
        panelformulario.add(txtfecha);
        panelformulario.add(new JLabel("Hora de inicio (hh:mm):"));
        txthora = new JTextField();
        panelformulario.add(txthora);
        panelsuperior.add(panelformulario, BorderLayout.CENTER);

        JPanel panelbotones = new JPanel(new GridLayout(1, 6, 10, 10));
        panelbotones.add(crearBoton("Nuevo"));
        panelbotones.add(crearBoton("Programar"));
        panelbotones.add(crearBoton("Buscar"));
        panelbotones.add(crearBoton("Modificar"));
        panelbotones.add(crearBoton("Cancelar cita"));
        panelbotones.add(crearBoton("Cerrar"));
        panelsuperior.add(panelbotones, BorderLayout.SOUTH);

        panelprincipal.add(panelsuperior, BorderLayout.NORTH);

        tablacitas = new JTable();
        JScrollPane panelscroll = new JScrollPane(tablacitas);
        panelscroll.setBorder(BorderFactory.createTitledBorder("Citas registradas"));
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
        } else if (comando.equals("Programar")) {
            programar();
        } else if (comando.equals("Buscar")) {
            buscar();
        } else if (comando.equals("Modificar")) {
            modificar();
        } else if (comando.equals("Cancelar cita")) {
            cancelar();
        } else if (comando.equals("Cerrar")) {
            dispose();
        }
    }

    private void limpiarCampos() {
        txtnumero.setText("");
        txtcodigomascota.setText("");
        if (veterinarios.length > 0) {
            cboveterinario.setSelectedIndex(0);
        }
        if (servicios.length > 0) {
            cboservicio.setSelectedIndex(0);
        }
        txtfecha.setText("");
        txthora.setText("");
        txtcodigomascota.requestFocus();
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación", JOptionPane.WARNING_MESSAGE);
    }

    // Devuelve el veterinario que está elegido en el combo (o null si no hay)
    private Empleado getVeterinarioElegido() {
        String elegido = String.valueOf(cboveterinario.getSelectedItem());
        for (int i = 0; i < veterinarios.length; i++) {
            if (textoveterinarios[i].equals(elegido)) {
                return veterinarios[i];
            }
        }
        return null;
    }

    // Devuelve el servicio que está elegido en el combo (o null si no hay)
    private Servicio getServicioElegido() {
        String elegido = String.valueOf(cboservicio.getSelectedItem());
        for (int i = 0; i < servicios.length; i++) {
            if (textoservicios[i].equals(elegido)) {
                return servicios[i];
            }
        }
        return null;
    }

    // Valida veterinario, servicio, fecha, hora, disponibilidad, horario de atención y cruces.
    // Devuelve "" si todo está bien, o el mensaje del primer error. "excepto" es la cita que se modifica (o null).
    private String validarCita(Cita excepto) {
        String fecha = txtfecha.getText().trim();
        String hora = txthora.getText().trim();
        Empleado veterinario = getVeterinarioElegido();
        Servicio servicio = getServicioElegido();

        if (veterinario == null) {
            return "No hay veterinarios registrados. Regístrelos primero en Empleados.";
        }
        if (servicio == null) {
            return "No hay servicios registrados. Regístrelos primero en Servicios.";
        }
        if (fecha.isEmpty() || hora.isEmpty()) {
            return "La fecha y la hora son obligatorias.";
        }
        if (!validaciones.esFechaValida(fecha)) {
            return "La fecha no es válida. Use el formato dd/mm/aaaa.";
        }
        if (!validaciones.esHoraValida(hora)) {
            return "La hora no es válida. Use el formato hh:mm (24 horas).";
        }
        if (!veterinario.estaDisponible()) {
            return "El veterinario seleccionado no está disponible.";
        }
        int inicio = validaciones.horaAMinutos(hora);
        if (inicio < aperturaminutos || inicio + servicio.getDuracion() > cierreminutos) {
            return "La cita debe empezar y terminar dentro del horario de atención (08:00 a 18:00). "
                    + "Este servicio dura " + servicio.getDuracion() + " minutos.";
        }
        if (gestorcitas.existeCruce(veterinario, fecha, inicio, servicio.getDuracion(), excepto)) {
            return "El veterinario ya tiene otra cita que se cruza con ese horario.";
        }
        return "";
    }

    private void programar() {
        String codigomascota = txtcodigomascota.getText().trim();
        if (codigomascota.isEmpty()) {
            mostrarAdvertencia("Escriba el código de la mascota.");
            return;
        }
        Mascota mascota = gestormascotas.buscarPorCodigo(codigomascota);
        if (mascota == null) {
            mostrarAdvertencia("No existe una mascota con ese código. Regístrela primero en Mascotas.");
            return;
        }
        String error = validarCita(null);
        if (!error.equals("")) {
            mostrarAdvertencia(error);
            return;
        }
        if (gestorcitas.estaLleno()) {
            mostrarAdvertencia("No hay espacio para más citas.");
            return;
        }

        int numero = gestorcitas.getSiguienteNumero();
        Cita nueva = new Cita(numero, mascota, getVeterinarioElegido(), getServicioElegido(),
                txtfecha.getText().trim(), txthora.getText().trim());
        if (gestorcitas.registrar(nueva)) {
            JOptionPane.showMessageDialog(this, "Cita programada correctamente. N° de cita: " + numero);
            llenarTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo programar la cita.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Devuelve la cita del N° escrito, o null (mostrando el error) si el número no es válido o no existe
    private Cita buscarCitaEscrita() {
        String numerotexto = txtnumero.getText().trim();
        if (!numerotexto.matches("\\d+")) {
            mostrarAdvertencia("Escriba el N° de la cita (solo dígitos).");
            return null;
        }
        Cita cita = gestorcitas.buscarPorNumero(Integer.valueOf(numerotexto));
        if (cita == null) {
            mostrarAdvertencia("No existe una cita con ese número.");
        }
        return cita;
    }

    private void buscar() {
        Cita cita = buscarCitaEscrita();
        if (cita == null) {
            return;
        }
        txtcodigomascota.setText(cita.getMascota().getCodigoMascota());
        for (int i = 0; i < veterinarios.length; i++) {
            if (veterinarios[i] == cita.getVeterinario()) {
                cboveterinario.setSelectedIndex(i);
            }
        }
        for (int i = 0; i < servicios.length; i++) {
            if (servicios[i] == cita.getServicio()) {
                cboservicio.setSelectedIndex(i);
            }
        }
        txtfecha.setText(cita.getFecha());
        txthora.setText(cita.getHora());
    }

    // Modificar veterinario, servicio, fecha u hora de una cita Programada (la mascota no se cambia)
    private void modificar() {
        Cita cita = buscarCitaEscrita();
        if (cita == null) {
            return;
        }
        if (!cita.getEstado().equals("Programada")) {
            mostrarAdvertencia("Solo se pueden modificar citas en estado Programada.");
            return;
        }
        if (!txtcodigomascota.getText().trim().equalsIgnoreCase(cita.getMascota().getCodigoMascota())) {
            mostrarAdvertencia("La mascota de una cita no se puede cambiar. Cancele la cita y programe otra.");
            return;
        }
        String error = validarCita(cita);
        if (!error.equals("")) {
            mostrarAdvertencia(error);
            return;
        }

        cita.setVeterinario(getVeterinarioElegido());
        cita.setServicio(getServicioElegido());
        cita.setFecha(txtfecha.getText().trim());
        cita.setHora(txthora.getText().trim());
        JOptionPane.showMessageDialog(this, "Cita modificada correctamente.");
        llenarTabla();
        limpiarCampos();
    }

    // La cita no se borra: pasa a estado "Cancelada" y deja de ocupar el horario del veterinario
    private void cancelar() {
        Cita cita = buscarCitaEscrita();
        if (cita == null) {
            return;
        }
        if (!cita.getEstado().equals("Programada")) {
            mostrarAdvertencia("Solo se pueden cancelar citas en estado Programada.");
            return;
        }
        // TODO: pedir confirmación antes de cancelar (cuando se vea el cuadro de diálogo de confirmación)
        cita.setEstado("Cancelada");
        JOptionPane.showMessageDialog(this, "Cita cancelada correctamente.");
        llenarTabla();
        limpiarCampos();
    }

    private void llenarTabla() {
        modelotabla = new DefaultTableModel();
        tablacitas.setModel(modelotabla);
        modelotabla.addColumn("N°");
        modelotabla.addColumn("Fecha");
        modelotabla.addColumn("Hora");
        modelotabla.addColumn("Mascota");
        modelotabla.addColumn("Dueño");
        modelotabla.addColumn("Veterinario");
        modelotabla.addColumn("Servicio");
        modelotabla.addColumn("Duración (min)");
        modelotabla.addColumn("Estado");

        Cita[] lista = gestorcitas.getLista();
        for (int i = 0; i < lista.length; i++) {
            Cita cita = lista[i];
            String[] fila = new String[9];
            fila[0] = String.valueOf(cita.getNumero());
            fila[1] = cita.getFecha();
            fila[2] = cita.getHora();
            fila[3] = cita.getMascota().getCodigoMascota() + " - " + cita.getMascota().getNombre();
            fila[4] = cita.getMascota().getCliente().getNombreCompleto();
            fila[5] = cita.getVeterinario().getNombreCompleto();
            fila[6] = cita.getServicio().getNombre();
            fila[7] = String.valueOf(cita.getServicio().getDuracion());
            fila[8] = cita.getEstado();
            modelotabla.addRow(fila);
        }
    }
}