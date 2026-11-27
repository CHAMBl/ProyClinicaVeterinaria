/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author josec
 */
public interface Reporte {

    // aca ponemos toda la tabla columnas y filas
    String getNombre();

    String[] getColumnas();

    String[][] generar();
}
