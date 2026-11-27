/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author bonif
 */
public interface Descuento {

    // Devuelve cuánto dinero se descuenta de un monto
    double calcular(double monto);

    // Nombre de la categoría de cliente que corresponde a este descuento
    String getNombre();
}
