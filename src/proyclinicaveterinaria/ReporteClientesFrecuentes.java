/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author josec
 */
public class ReporteClientesFrecuentes implements Reporte {
    private GestorClientes gestorclientes;

    public ReporteClientesFrecuentes(GestorClientes gestorclientes) {
        this.gestorclientes = gestorclientes;
    }

    @Override
    public String getNombre() {
        return "Clientes frecuentes";
    }

    @Override
    public String[] getColumnas() {
        return new String[]{"DNI", "Cliente", "Teléfono", "Atenciones finalizadas", "Categoría"};
    }

    @Override
    public String[][] generar() {
        Cliente[] clientes = gestorclientes.getLista();
        int cantidadfrecuentes = 0;
        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i].getDescuento().getNombre().equals("Frecuente")) {
                cantidadfrecuentes++;
            }
        }
        String[][] filas = new String[cantidadfrecuentes][5];
        int fila = 0;
        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i].getDescuento().getNombre().equals("Frecuente")) {
                filas[fila][0] = String.valueOf(clientes[i].getDni());
                filas[fila][1] = clientes[i].getNombreCompleto();
                filas[fila][2] = clientes[i].getTelefono();
                filas[fila][3] = String.valueOf(clientes[i].getAtencionesFinalizadas());
                filas[fila][4] = clientes[i].getDescuento().getNombre();
                fila++;
            }
        }
        return filas;
    }
}
