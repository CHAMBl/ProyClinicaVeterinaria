/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author bonif
 */
public class Recepcionista extends Empleado {
    
    public Recepcionista(String nombres, String apellidos, int dni) {
        super(nombres, apellidos, dni);
    }
    
    @Override
    public String getPermisos() {
        return "Gestiona clientes y mascotas. Programa citas.";
    }
}
