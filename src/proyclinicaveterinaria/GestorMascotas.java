/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author bonif
 */
public class GestorMascotas {
    private Mascota[] mascotas;
    private int cantidad;

    public GestorMascotas(int capacidad) {
        mascotas = new Mascota[capacidad];
        cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean estaLleno() {
        return cantidad == mascotas.length;
    }

    // Devuelve la mascota con ese código, o null si no existe
    public Mascota buscarPorCodigo(String codigo) {
        for (int i = 0; i < cantidad; i++) {
            if (mascotas[i].getCodigoMascota().equalsIgnoreCase(codigo)) {
                return mascotas[i];
            }
        }
        return null;
    }

    public boolean registrar(Mascota m) {
        if (estaLleno() || buscarPorCodigo(m.getCodigoMascota()) != null) {
            return false;
        }
        mascotas[cantidad] = m;
        cantidad++;
        return true;
    }

    public boolean eliminar(String codigo) {
        for (int i = 0; i < cantidad; i++) {
            if (mascotas[i].getCodigoMascota().equalsIgnoreCase(codigo)) {
                for (int j = i; j < cantidad - 1; j++) {
                    mascotas[j] = mascotas[j + 1];
                }
                cantidad--;
                mascotas[cantidad] = null;
                return true;
            }
        }
        return false;
    }

    // Devuelve solo las mascotas registradas (para llenar la tabla de la GUI)
    public Mascota[] getLista() {
        Mascota[] lista = new Mascota[cantidad];
        for (int i = 0; i < cantidad; i++) {
            lista[i] = mascotas[i];
        }
        return lista;
    }
}
