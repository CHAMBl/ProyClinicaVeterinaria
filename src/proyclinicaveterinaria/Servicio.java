/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;


/**
 *
 * @author silver
 */
public class Servicio {
    private String codigo;
    private String nombre;
    private double preciobase;
    private int duracion; // esto tiene q estar en minutos

    public Servicio(String codigo, String nombre, double preciobase, int duracion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.preciobase = preciobase;
        this.duracion = duracion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPreciobase() {
        return preciobase;
    }

    public void setPreciobase(double preciobase) {
        this.preciobase = preciobase;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    
    public void mostrarDatos() {
        System.out.println("Código: " + codigo + " | " + nombre + " | Precio base: S/ " + preciobase + " | Duración: " + duracion + " min");
    }
    
    
}
