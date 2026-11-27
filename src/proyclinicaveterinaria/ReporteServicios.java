/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author josec
 */
public class ReporteServicios implements Reporte {
    private GestorServicios gestorservicios;
    private GestorComprobantes gestorcomprobantes;

    public ReporteServicios(GestorServicios gestorservicios, GestorComprobantes gestorcomprobantes) {
        this.gestorservicios = gestorservicios;
        this.gestorcomprobantes = gestorcomprobantes;
    }

    @Override
    public String getNombre() {
        return "Servicios más solicitados";
    }

    @Override
    public String[] getColumnas() {
        return new String[]{"Código", "Servicio", "Atenciones", "Ingreso por servicio (S/)"};
    }

    @Override
    public String[][] generar() {
        Servicio[] servicios = gestorservicios.getLista();
        Comprobante[] comprobantes = gestorcomprobantes.getLista();
        String[][] filas = new String[servicios.length][4];
        for (int i = 0; i < servicios.length; i++) {
            int atenciones = 0;
            double ingreso = 0;
            for (int j = 0; j < comprobantes.length; j++) {
                if (comprobantes[j].getServicio() == servicios[i]) {
                    atenciones++;
                    ingreso = ingreso + comprobantes[j].getTotalServicios();
                }
            }
            filas[i][0] = servicios[i].getCodigo();
            filas[i][1] = servicios[i].getNombre();
            filas[i][2] = String.valueOf(atenciones);
            filas[i][3] = String.format("%.2f", ingreso); // el format me ayuda a meterle espaciado
        }
        return filas;
    }
}
