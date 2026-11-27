/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;


public abstract class Comprobante {
    private int numero;                  // numero correlativo
    private String fecha;                // fecha
    private AtencionVeterinaria atencion;
    private Cliente cliente;

    private double totalservicios;
    private double totalmedicamentos;
    private double descuento;
    private double subtotal;
    private double igv;
    private double total;

    public Comprobante(int numero, AtencionVeterinaria atencion) {
        this.numero = numero;
        this.atencion = atencion;
        this.fecha = atencion.getCita().getFecha();
        this.cliente = atencion.getCita().getMascota().getCliente();

        totalservicios = atencion.getCita().getServicio().getPreciobase();
        totalmedicamentos = atencion.getTotalMedicamentos();
        descuento = redondear(cliente.calcularDescuento(totalservicios));
        subtotal = redondear(totalservicios + totalmedicamentos - descuento);
        igv = redondear(subtotal * 0.18);
        total = redondear(subtotal + igv);
    }


    private double redondear(double monto) {
        return (int) (monto * 100 + 0.5) / 100.0;
    }


    public abstract String getTipo();

    public abstract String getSerie();

    public abstract String getDatosCliente();

    public int getNumero() { return numero; }
    public String getFecha() { return fecha; }
    public AtencionVeterinaria getAtencion() { return atencion; }
    public Cliente getCliente() { return cliente; }
    public Servicio getServicio() { return atencion.getCita().getServicio(); }
    public double getTotalServicios() { return totalservicios; }
    public double getTotalMedicamentos() { return totalmedicamentos; }
    public double getDescuento() { return descuento; }
    public double getSubtotal() { return subtotal; }
    public double getIgv() { return igv; }
    public double getTotal() { return total; }


    public String getTextoComprobante() {
        return getTipo().toUpperCase() + " " + getSerie() + "\n"
                + "Fecha: " + fecha + "\n"
                + getDatosCliente() + "\n"
                + "Mascota: " + atencion.getCita().getMascota().getNombre() + "\n"
                + "Servicio: " + getServicio().getNombre() + "  S/ " + String.format("%.2f", totalservicios) + "\n"
                + "Medicamentos: " + atencion.getTextoMedicamentos() + "  S/ " + String.format("%.2f", totalmedicamentos) + "\n"
                + "Descuento (" + cliente.getDescuento().getNombre() + "): -S/ " + String.format("%.2f", descuento) + "\n"
                + "Subtotal: S/ " + String.format("%.2f", subtotal) + "\n"
                + "IGV (18%): S/ " + String.format("%.2f", igv) + "\n"
                + "TOTAL: S/ " + String.format("%.2f", total);
    }
}