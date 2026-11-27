package proyclinicaveterinaria;

/**
 * NUEVA CLASE (RF06): guarda las atenciones en un arreglo de objetos (una por cita atendida).
 */
public class GestorAtenciones {
    private AtencionVeterinaria[] atenciones;
    private int cantidad;

    public GestorAtenciones(int capacidad) {
        atenciones = new AtencionVeterinaria[capacidad];
        cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean estaLleno() {
        return cantidad == atenciones.length;
    }

    // Devuelve la atención de esa cita, o null si la cita todavía no se atendió
    public AtencionVeterinaria buscarPorNumeroCita(int numerocita) {
        for (int i = 0; i < cantidad; i++) {
            if (atenciones[i].getCita().getNumero() == numerocita) {
                return atenciones[i];
            }
        }
        return null;
    }

    public boolean registrar(AtencionVeterinaria a) {
        if (estaLleno() || buscarPorNumeroCita(a.getCita().getNumero()) != null) {
            return false;
        }
        atenciones[cantidad] = a;
        cantidad++;
        return true;
    }

    // Devuelve solo las atenciones registradas
    public AtencionVeterinaria[] getLista() {
        AtencionVeterinaria[] lista = new AtencionVeterinaria[cantidad];
        for (int i = 0; i < cantidad; i++) {
            lista[i] = atenciones[i];
        }
        return lista;
    }
}
