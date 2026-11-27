/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author josec
 */
public class ReporteIngresos implements Reporte {
    private GestorComprobantes gestorcomprobantes;

    public ReporteIngresos(GestorComprobantes gestorcomprobantes) {
        this.gestorcomprobantes = gestorcomprobantes;
    }

    @Override
    public String getNombre() {
        return "Ingresos";
    }

    @Override
    public String[] getColumnas() {
        return new String[]{"Tipo", "Cantidad", "Subtotal (S/)", "Descuentos (S/)", "IGV (S/)", "Total (S/)"};
    }

    @Override
    public String[][] generar() {
        Comprobante[] lista = gestorcomprobantes.getLista();
        if (lista.length == 0) {
            return new String[0][];
        }
        String[] tipos = {"Boleta", "Factura"};
        String[][] filas = new String[3][6];
        int cantidadtotal = 0;
        double subtotaltotal = 0;
        double descuentototal = 0;
        double igvtotal = 0;
        double totaltotal = 0;

        for (int t = 0; t < tipos.length; t++) {
            int cantidad = 0;
            double subtotal = 0;
            double descuento = 0;
            double igv = 0;
            double total = 0;
            for (int i = 0; i < lista.length; i++) {
                if (lista[i].getTipo().equals(tipos[t])) {
                    cantidad++;
                    subtotal = subtotal + lista[i].getSubtotal();
                    descuento = descuento + lista[i].getDescuento();
                    igv = igv + lista[i].getIgv();
                    total = total + lista[i].getTotal();
                }
            }
            filas[t][0] = tipos[t] + "s";
            filas[t][1] = String.valueOf(cantidad);
            filas[t][2] = String.format("%.2f", subtotal);
            filas[t][3] = String.format("%.2f", descuento);
            filas[t][4] = String.format("%.2f", igv);
            filas[t][5] = String.format("%.2f", total);
            cantidadtotal = cantidadtotal + cantidad;
            subtotaltotal = subtotaltotal + subtotal;
            descuentototal = descuentototal + descuento;
            igvtotal = igvtotal + igv;
            totaltotal = totaltotal + total;
        }
        filas[2][0] = "TOTAL";
        filas[2][1] = String.valueOf(cantidadtotal);
        filas[2][2] = String.format("%.2f", subtotaltotal);
        filas[2][3] = String.format("%.2f", descuentototal);
        filas[2][4] = String.format("%.2f", igvtotal);
        filas[2][5] = String.format("%.2f", totaltotal);
        return filas;
    }
}
