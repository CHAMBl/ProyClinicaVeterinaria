/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author josec
 */
public class Factura extends Comprobante {
    private String ruc;
    private String razonsocial;

    public Factura(int numero, AtencionVeterinaria atencion, String ruc, String razonsocial) {
        super(numero, atencion);
        this.ruc = ruc;
        this.razonsocial = razonsocial;
    }

    public String getRuc() { return ruc; }
    public String getRazonsocial() { return razonsocial; }

    @Override
    public String getTipo() {
        return "Factura";
    }

    @Override
    public String getSerie() {
        return "F001-" + getNumero();
    }

    @Override
    public String getDatosCliente() {
        return "Razón social: " + razonsocial + " (RUC " + ruc + ")";
    }
}