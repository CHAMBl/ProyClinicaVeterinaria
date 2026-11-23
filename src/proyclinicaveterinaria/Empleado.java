/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author bonif
 */
public class Empleado extends Persona {
    private String usuario;
    private String contraseña;
    private String rol;

    public Empleado(String nombres, String apellidos, int dni) {
        super(nombres, apellidos, dni);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
    public String getPermisos() { //aca hay polimorfismo - osea cada trabajador tendra distinto cada uno
        return "";
    }

    public boolean estaDisponible() { // SOLO EL VETERINARIO PUEDE ESTAR NO DISPONIBLE, LOS DEMAS POR DEFECTO TENDRAN TRUE ESTO
        return true;
    }

    public void setDisponible(boolean disponible) { // algo q usara el veterinario para ver si esta o no disponible (como es boolean es 1 o 0)
    }

    @Override
    public void mostrarDatos() {
        System.out.println("DNI: "+dni);
        System.out.println("Nombres: "+nombres);
        System.out.println("Apellidos: "+apellidos);
        System.out.println("Usuario: "+usuario);
        System.out.println("Contraseña: "+contraseña);
        System.out.println("Rol: "+rol);
    }
}
