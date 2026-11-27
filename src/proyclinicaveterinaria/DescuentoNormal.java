/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author bonif
 */
public class DescuentoNormal implements Descuento {

    @Override
    public double calcular(double monto) {
        return 0;
    }

    @Override
    public String getNombre() {
        return "Normal";
    }
}
