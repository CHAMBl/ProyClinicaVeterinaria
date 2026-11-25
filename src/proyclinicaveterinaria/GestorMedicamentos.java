/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author silver
 */
public class GestorMedicamentos {
    private Medicamento[] medicamentos;
    private int cantidad;

    public GestorMedicamentos(int capacidad) {
        medicamentos = new Medicamento[capacidad];
        cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean estaLleno() {
        return cantidad == medicamentos.length;
    }

    // Devuelve el medicamento con ese código, o null si no existe
    public Medicamento buscarPorCodigo(String codigo) {
        for (int i = 0; i < cantidad; i++) {
            if (medicamentos[i].getCodigo().equalsIgnoreCase(codigo)) {
                return medicamentos[i];
            }
        }
        return null;
    }

    public boolean registrar(Medicamento m) {
        if (estaLleno() || buscarPorCodigo(m.getCodigo()) != null) {
            return false;
        }
        medicamentos[cantidad] = m;
        cantidad++;
        return true;
    }

    public boolean eliminar(String codigo) {
        for (int i = 0; i < cantidad; i++) {
            if (medicamentos[i].getCodigo().equalsIgnoreCase(codigo)) {
                for (int j = i; j < cantidad - 1; j++) {
                    medicamentos[j] = medicamentos[j + 1];
                }
                cantidad--;
                medicamentos[cantidad] = null;
                return true;
            }
        }
        return false;
    }

    // Devuelve solo los medicamentos registrados (para llenar la tabla de la GUI)
    public Medicamento[] getLista() {
        Medicamento[] lista = new Medicamento[cantidad];
        for (int i = 0; i < cantidad; i++) {
            lista[i] = medicamentos[i];
        }
        return lista;
    } 
}
