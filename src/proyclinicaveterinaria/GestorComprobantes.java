/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author josec
 */
public class GestorComprobantes {
    private Comprobante[] comprobantes;
    private int cantidad;
    private int siguientenumero;

    public GestorComprobantes(int capacidad) {
        comprobantes = new Comprobante[capacidad];
        cantidad = 0;
        siguientenumero = 1;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean estaLleno() {
        return cantidad == comprobantes.length;
    }

    public int getSiguienteNumero() {
        return siguientenumero;
    }

    public Comprobante buscarPorNumero(int numero) {
        for (int i = 0; i < cantidad; i++) {
            if (comprobantes[i].getNumero() == numero) {
                return comprobantes[i];
            }
        }
        return null;
    }

    public boolean registrar(Comprobante c) {
        if (estaLleno()) {
            return false;
        }
        comprobantes[cantidad] = c;
        cantidad++;
        siguientenumero++;
        return true;
    }

    
    public Comprobante[] getLista() {
        Comprobante[] lista = new Comprobante[cantidad];
        for (int i = 0; i < cantidad; i++) {
            lista[i] = comprobantes[i];
        }
        return lista;
    }
}
