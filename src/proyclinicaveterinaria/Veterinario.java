/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author bonif
 */
public class Veterinario extends Empleado {
    
    private boolean disponible;

    public Veterinario(boolean disponible, String nombres, String apellidos, int dni) {
        super(nombres, apellidos, dni);
        this.disponible = disponible;
    }
   
    @Override
    public boolean estaDisponible() {
        return disponible;
    }

    @Override
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String getPermisos() {
        return "Registra diagnosticos y tratamientos. Finaliza atenciones.";
    }

    @Override
    public void mostrarDatos() {
        super.mostrarDatos(); // aca se muestra lo q esta puesto en empleado
        if (disponible) {
            System.out.println("Disponible: Si");
        } else {
            System.out.println("Disponible: No");
        }
    }
       
}
