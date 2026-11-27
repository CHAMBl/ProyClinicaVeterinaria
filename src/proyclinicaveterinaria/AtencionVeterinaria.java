package proyclinicaveterinaria;

/**
 * NUEVA CLASE (RF06): lo que se hace realmente en una cita (diagnóstico, tratamiento y medicamentos usados).
 * El estado (Programada, En Atención, Finalizada, Cancelada) y su historial viven en la Cita.
 */
public class AtencionVeterinaria {
    private Cita cita;
    private String diagnostico;
    private String tratamiento;

    // Medicamentos usados y cuántas unidades de cada uno (el elemento i de un arreglo va con el elemento i del otro)
    private Medicamento[] medicamentos = new Medicamento[10];
    private int[] unidades = new int[10];
    private int cantidadmedicamentos = 0;

    public AtencionVeterinaria(Cita cita) {
        this.cita = cita;
        this.diagnostico = "";
        this.tratamiento = "";
    }

    public Cita getCita() { return cita; }
    public String getDiagnostico() { return diagnostico; }
    public String getTratamiento() { return tratamiento; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    // Agrega unidades de un medicamento. Si ya se había usado, suma las unidades. Devuelve false si no hay espacio.
    public boolean agregarMedicamento(Medicamento medicamento, int cantidad) {
        for (int i = 0; i < cantidadmedicamentos; i++) {
            if (medicamentos[i] == medicamento) {
                unidades[i] = unidades[i] + cantidad;
                return true;
            }
        }
        if (cantidadmedicamentos == medicamentos.length) {
            return false;
        }
        medicamentos[cantidadmedicamentos] = medicamento;
        unidades[cantidadmedicamentos] = cantidad;
        cantidadmedicamentos++;
        return true;
    }

    // Se usa al cancelar una atención que estaba En Atención: devuelve el stock descontado
    public void devolverStock() {
        for (int i = 0; i < cantidadmedicamentos; i++) {
            medicamentos[i].aumentarStock(unidades[i]);
            unidades[i] = 0;
        }
    }

    // Texto para mostrar en la tabla, por ejemplo: "Amoxicilina x2, Vitamina x1"
    public String getTextoMedicamentos() {
        if (cantidadmedicamentos == 0) {
            return "-";
        }
        String texto = "";
        for (int i = 0; i < cantidadmedicamentos; i++) {
            if (i > 0) {
                texto = texto + ", ";
            }
            texto = texto + medicamentos[i].getNombre() + " x" + unidades[i];
        }
        return texto;
    }

    double getTotalMedicamentos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
