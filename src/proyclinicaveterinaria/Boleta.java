/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author josec
 */
public class Boleta extends Comprobante {

    public Boleta(int numero, AtencionVeterinaria atencion) {
        super(numero, atencion);
    }

    @Override
    public String getTipo() {
        return "Boleta";
    }

    @Override
    public String getSerie() {
        return "B001-" + getNumero();
    }

    @Override
    public String getDatosCliente() {
        return "Cliente: " + getCliente().getNombreCompleto() + " (DNI " + getCliente().getDni() + ")";
    }
}
