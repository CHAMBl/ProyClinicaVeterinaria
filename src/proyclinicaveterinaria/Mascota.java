/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author silver
 */
public abstract class Mascota {
    protected String codigoMascota;
    protected String nombre;
    protected String especie;
    protected String raza;
    protected String sexo;
    protected String fechaNacimiento;
    protected double peso;
    protected String estado;
    protected Cliente cliente;

    public Mascota(String codigoMascota, String nombre, String especie, String raza, String sexo, String fechaNacimiento, double peso, String estado, Cliente cliente) {
        this.codigoMascota = codigoMascota;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
        this.estado = estado;
        this.cliente = cliente;
    }

    public String getCodigoMascota() {
        return codigoMascota;
    }

    public void setCodigoMascota(String codigoMascota) {
        this.codigoMascota = codigoMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public abstract double calcularDosis();
    
    protected void mostrarDatosComunes() {
        System.out.println("Código: " + codigoMascota + " | Especie: " + especie);
        System.out.println("Nombre: " + nombre + " | Raza: " + raza + " | Sexo: " + sexo);
        System.out.println("Fecha de nacimiento: " + fechaNacimiento + " | Peso: " + peso + " kg");
        System.out.println("Estado: " + estado);
        System.out.println("Cliente: " + cliente.getNombreCompleto() + " (DNI " + cliente.getDni() + ")");
    }
    
    public abstract void mostrarDatos();
    
}
