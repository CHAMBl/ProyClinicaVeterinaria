/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author bonif
 */
public class Administrador extends Empleado {

    public Administrador(String nombres, String apellidos, int dni) {
        super(nombres, apellidos, dni);
    }
    
    @Override
    public String getPermisos() {
        return "Gestiona empleados y servicios.";
    }
    
}
