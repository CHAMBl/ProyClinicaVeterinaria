/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author silver
 */
public class Medicamento {
    private String codigo;
    private String nombre;
    private int stock;
    private double preciounitario;
    private String fechavencimiento; // formato dd/mm/aaaa

    public Medicamento(String codigo, String nombre, int stock, double preciounitario, String fechavencimiento) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.stock = stock;
        this.preciounitario = preciounitario;
        this.fechavencimiento = fechavencimiento;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getStock() { return stock; }
    public double getPreciounitario() { return preciounitario; }
    public String getFechavencimiento() { return fechavencimiento; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setStock(int stock) { this.stock = stock; }
    public void setPreciounitario(double preciounitario) { this.preciounitario = preciounitario; }
    public void setFechavencimiento(String fechavencimiento) { this.fechavencimiento = fechavencimiento; }

    // true si el medicamento ya venció respecto a la fecha dada (dd/mm/aaaa)
    public boolean estaVencido(String fechareferencia) {
        Validaciones validaciones = new Validaciones();
        return validaciones.fechaANumero(fechavencimiento) < validaciones.fechaANumero(fechareferencia);
    }

    // Se usará en RF06: devuelve "" si se puede usar, o el mensaje de error si no
    public String validarUso(int unidades, String fechareferencia) {
        if (unidades <= 0) {
            return "Las unidades deben ser mayores que cero.";
        }
        if (estaVencido(fechareferencia)) {
            return "El medicamento " + nombre + " está vencido.";
        }
        if (unidades > stock) {
            return "Stock insuficiente de " + nombre + " (disponible: " + stock + ").";
        }
        return "";
    }

    // Se usará en RF06 al registrar un medicamento en una atención
    public void descontarStock(int cantidad) {
        stock = stock - cantidad;
    }

    // Se usará si se cancela una atención y hay que devolver lo descontado
    public void aumentarStock(int cantidad) {
        stock = stock + cantidad;
    }
    
}
