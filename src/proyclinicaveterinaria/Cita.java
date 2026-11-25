/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author silver
 */
public class Cita {
    private int numero;
    private Mascota mascota;
    private Empleado veterinario;
    private Servicio servicio;
    private String fecha;  // dd/mm/aaaa
    private String hora;   // hh:mm
    private String estado;

    public Cita(int numero, Mascota mascota, Empleado veterinario, Servicio servicio, String fecha, String hora) {
        this.numero = numero;
        this.mascota = mascota;
        this.veterinario = veterinario;
        this.servicio = servicio;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = "Programada";
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
    public void setEstado(String estado) { this.estado = estado; }
}
