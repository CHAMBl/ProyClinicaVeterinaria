package proyclinicaveterinaria;

/**
 * CLASE MODIFICADA (RF05 y RF06): una cita. Relaciona una Mascota, un veterinario (Empleado) y un Servicio.
 * Estados: Programada -> En Atención -> Finalizada   (o Cancelada).
 * Cada cambio de estado queda guardado en un arreglo (historial).
 */
public class Cita {
    private int numero;
    private Mascota mascota;
    private Empleado veterinario;
    private Servicio servicio;
    private String fecha;  // dd/mm/aaaa
    private String hora;   // hh:mm
    private String estado;

    // Historial de cambios de estado
    private String[] historial = new String[20];
    private int cantidadhistorial = 0;

    public Cita(int numero, Mascota mascota, Empleado veterinario, Servicio servicio, String fecha, String hora) {
        this.numero = numero;
        this.mascota = mascota;
        this.veterinario = veterinario;
        this.servicio = servicio;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = "Programada";
        historial[0] = "Cita creada: Programada";
        cantidadhistorial = 1;
    }

    public int getNumero() { return numero; }
    public Mascota getMascota() { return mascota; }
    public Empleado getVeterinario() { return veterinario; }
    public Servicio getServicio() { return servicio; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getEstado() { return estado; }

    public void setVeterinario(Empleado veterinario) { this.veterinario = veterinario; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setHora(String hora) { this.hora = hora; }

    // Cambia el estado y deja constancia en el historial (reemplaza al antiguo setEstado)
    public void cambiarEstado(String nuevoestado) {
        if (cantidadhistorial < historial.length) {
            historial[cantidadhistorial] = estado + " -> " + nuevoestado;
            cantidadhistorial++;
        }
        estado = nuevoestado;
    }

    // Devuelve el historial como un solo texto, una línea por cambio
    public String getTextoHistorial() {
        String texto = "";
        for (int i = 0; i < cantidadhistorial; i++) {
            texto = texto + (i + 1) + ". " + historial[i] + "\n";
        }
        return texto;
    }
}