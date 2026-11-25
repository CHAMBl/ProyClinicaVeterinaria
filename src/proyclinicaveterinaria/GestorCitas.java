/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author silver
 */
public class GestorCitas {
    private Cita[] citas;
    private int cantidad;
    private int siguientenumero;
    private Validaciones validaciones = new Validaciones();

    public GestorCitas(int capacidad) {
        citas = new Cita[capacidad];
        cantidad = 0;
        siguientenumero = 1;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean estaLleno() {
        return cantidad == citas.length;
    }

    // Número que tendrá la próxima cita registrada
    public int getSiguienteNumero() {
        return siguientenumero;
    }

    public Cita buscarPorNumero(int numero) {
        for (int i = 0; i < cantidad; i++) {
            if (citas[i].getNumero() == numero) {
                return citas[i];
            }
        }
        return null;
    }

    public boolean registrar(Cita c) {
        if (estaLleno()) {
            return false;
        }
        citas[cantidad] = c;
        cantidad++;
        siguientenumero++;
        return true;
    }

    // true si el veterinario ya tiene otra cita (no cancelada) que se cruza con ese horario.
    // "excepto" es la cita que se está modificando (para que no se cruce consigo misma); puede ser null.
    public boolean existeCruce(Empleado veterinario, String fecha, int inicio, int duracion, Cita excepto) {
        int fin = inicio + duracion;
        for (int i = 0; i < cantidad; i++) {
            Cita c = citas[i];
            if (c != excepto && c.getVeterinario() == veterinario && c.getFecha().equals(fecha)
                    && !c.getEstado().equals("Cancelada")) {
                int cinicio = validaciones.horaAMinutos(c.getHora());
                int cfin = cinicio + c.getServicio().getDuracion();
                if (inicio < cfin && cinicio < fin) {
                    return true;
                }
            }
        }
        return false;
    }

    // Devuelve solo las citas registradas (para llenar la tabla de la GUI)
    public Cita[] getLista() {
        Cita[] lista = new Cita[cantidad];
        for (int i = 0; i < cantidad; i++) {
            lista[i] = citas[i];
        }
        return lista;
    }
}
