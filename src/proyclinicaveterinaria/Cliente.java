/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author bonif
 */
public class Cliente extends Persona {
    private String telefono;
    private String correo;
    private String direccion;
    private int atencionesfinalizadas = 0;
    private Descuento descuento = new DescuentoNormal();

    public Cliente(String telefono, String correo, String direccion, String nombres, String apellidos, int dni) {
        super(nombres, apellidos, dni);
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

        // NUEVO (RF09)
    public int getAtencionesFinalizadas() {
        return atencionesfinalizadas;
    }

    // NUEVO (RF09)
    public Descuento getDescuento() {
        return descuento;
    }

    // NUEVO (RF09): se llama cada vez que se finaliza una atención de este cliente.
    // Con la 5ta atención finalizada pasa a ser Cliente Frecuente (el descuento se aplica desde la siguiente).
    public void sumarAtencionFinalizada() {
        atencionesfinalizadas = atencionesfinalizadas + 1;
        if (atencionesfinalizadas >= 5) {
            descuento = new DescuentoFrecuente();
        }
    }

    // NUEVO (RF09): cuánto se le descuenta de un monto (cada tipo de descuento lo calcula distinto)
    public double calcularDescuento(double monto) {
        return descuento.calcular(monto);
    }
    
    @Override
    public void mostrarDatos() {
        System.out.println("DNI: " + dni);
        System.out.println("Nombres: " + nombres);
        System.out.println("Apellidos: " + apellidos);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Correo: " + correo);
        System.out.println("Dirección: " + direccion);
    }
    
    
}
