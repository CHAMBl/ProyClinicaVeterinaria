/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author bonif
 */
public class GestorServicios {
    private Servicio[] servicios;
    private int cantidad;

    public GestorServicios(int capacidad) {
        servicios = new Servicio[capacidad];
        cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean estaLleno() {
        return cantidad == servicios.length;
    }

    // Devuelve el servicio con ese código, o null si no existe
    public Servicio buscarPorCodigo(String codigo) {
        for (int i = 0; i < cantidad; i++) {
            if (servicios[i].getCodigo().equalsIgnoreCase(codigo)) {
                return servicios[i];
            }
        }
        return null;
    }

    public boolean registrar(Servicio s) {
        if (estaLleno() || buscarPorCodigo(s.getCodigo()) != null) {
            return false;
        }
        servicios[cantidad] = s;
        cantidad++;
        return true;
    }

    public boolean eliminar(String codigo) {
        for (int i = 0; i < cantidad; i++) {
            if (servicios[i].getCodigo().equalsIgnoreCase(codigo)) {
                for (int j = i; j < cantidad - 1; j++) {
                    servicios[j] = servicios[j + 1];
                }
                cantidad--;
                servicios[cantidad] = null;
                return true;
            }
        }
        return false;
    }

    // Devuelve solo los servicios registrados (para llenar la tabla de la GUI)
    public Servicio[] getLista() {
        Servicio[] lista = new Servicio[cantidad];
        for (int i = 0; i < cantidad; i++) {
            lista[i] = servicios[i];
        }
        return lista;
    }
}