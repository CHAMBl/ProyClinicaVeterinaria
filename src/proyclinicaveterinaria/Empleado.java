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
