/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author bonif
 */
public class DescuentoFrecuente implements Descuento {

    @Override
    public double calcular(double monto) {
        return monto * 0.10;
    }

    @Override
    public String getNombre() {
        return "Frecuente";
    }
}
