/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author josec
 */
public class GestorClientes {
    private Cliente[] clientes;
    private int cantidad;

    public GestorClientes(int capacidad) {
        clientes = new Cliente[capacidad];
        cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean estaLleno() {
        return cantidad == clientes.length;
    }

    public Cliente buscarPorDni(int dni) {
        for (int i = 0; i < cantidad; i++) {
            if (clientes[i].getDni() == dni) {
                return clientes[i];
            }
        }
        return null;
    }

    public boolean existeDni(int dni, Cliente excepto) {
        for (int i = 0; i < cantidad; i++) {
            if (clientes[i] != excepto && clientes[i].getDni() == dni) {
                return true;
            }
        }
        return false;
    }

    public boolean registrar(Cliente c) {
        if (estaLleno() || existeDni(c.getDni(), null)) {
            return false;
        }
        clientes[cantidad] = c;
        cantidad++;
        return true;
    }

    public boolean eliminar(int dni) {
        for (int i = 0; i < cantidad; i++) {
            if (clientes[i].getDni() == dni) {
                for (int j = i; j < cantidad - 1; j++) {
                    clientes[j] = clientes[j + 1];
                }
                cantidad--;
                clientes[cantidad] = null;
                return true;
            }
        }
        return false;
    }

    public void listar() {
        if (cantidad == 0) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        for (int i = 0; i < cantidad; i++) {
            clientes[i].mostrarDatos();
            System.out.println("----------------------------");
        }
    }
}
