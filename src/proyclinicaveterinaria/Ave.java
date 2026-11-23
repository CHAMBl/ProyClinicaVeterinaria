/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author silver
 */
public class Ave extends Mascota{

    public Ave(String codigoMascota, String nombre, String especie, String raza, String sexo, String fechaNacimiento, double peso, String estado, Cliente cliente) {
        super(codigoMascota, nombre, especie, raza, sexo, fechaNacimiento, peso, estado, cliente);
    }

    @Override
    public double calcularDosis() {
        // Seria 3 miligramos x cada kg de peso
        return peso*3;
        
    }
    @Override
    public void mostrarDatos() {
        mostrarDatosComunes();
        System.out.println("Dosis sugerida: " + calcularDosis() + " mg"); 
    }
    
}
