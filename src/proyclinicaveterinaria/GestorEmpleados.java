/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author josec
 */
public class GestorEmpleados {
    private Empleado[] empleados;
    private int cantidad;

    // el equals ignore case hace q por ejemplo el nombre usuario sea tmb aceptado como USUARIO o uSUario entre otros
    // 
    public GestorEmpleados(int capacidad) {
        empleados = new Empleado[capacidad];
        cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean estaLleno() {
        return cantidad == empleados.length;
    }

    public Empleado buscarPorDni(int dni) {
        for (int i = 0; i < cantidad; i++) {
            if (empleados[i].getDni() == dni) {
                return empleados[i];
            }
        }
        return null;
    }

    public boolean existeDni(int dni, Empleado excepto) {
        for (int i = 0; i < cantidad; i++) {
            if (empleados[i] != excepto && empleados[i].getDni() == dni) {
                return true;
            }
        }
        return false;
    }

    public boolean existeUsuario(String usuario, Empleado excepto) {
        for (int i = 0; i < cantidad; i++) {
            if (empleados[i] != excepto && empleados[i].getUsuario().equalsIgnoreCase(usuario)) {
                return true;
            }
        }
        return false;
    }

    public boolean registrar(Empleado e) {
        if (estaLleno() || existeDni(e.getDni(), null) || existeUsuario(e.getUsuario(), null)) {
            return false;
        }
        empleados[cantidad] = e;
        cantidad++;
        return true;
    }

    public boolean eliminar(int dni) {
        for (int i = 0; i < cantidad; i++) {
            if (empleados[i].getDni() == dni) {
                for (int j = i; j < cantidad - 1; j++) {
                    empleados[j] = empleados[j + 1];
                }
                cantidad--;
                empleados[cantidad] = null;
                return true;
            }
        }
        return false;
    }

    public Empleado autenticar(String usuario, String contraseña) {
        for (int i = 0; i < cantidad; i++) {
            if (empleados[i].getUsuario().equals(usuario) && empleados[i].getContraseña().equals(contraseña)) {
                return empleados[i];
            }
        }
        return null;
    }

    public void listar() {
        if (cantidad == 0) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        for (int i = 0; i < cantidad; i++) {
            empleados[i].mostrarDatos();
            System.out.println("----------------------------");
        }
    }
        // devuelve solo los empleados registrados (GUI)
    public Empleado[] getLista() {
        Empleado[] lista = new Empleado[cantidad];
        for (int i = 0; i < cantidad; i++) {
            lista[i] = empleados[i];
        }
        return lista;
    }
}
