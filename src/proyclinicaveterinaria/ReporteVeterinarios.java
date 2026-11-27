/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author josec
 */
public class ReporteVeterinarios implements Reporte {
    private GestorEmpleados gestorempleados;
    private GestorCitas gestorcitas;

    public ReporteVeterinarios(GestorEmpleados gestorempleados, GestorCitas gestorcitas) {
        this.gestorempleados = gestorempleados;
        this.gestorcitas = gestorcitas;
    }

    @Override
    public String getNombre() {
        return "Atenciones por veterinario";
    }

    @Override
    public String[] getColumnas() {
        return new String[]{"DNI", "Veterinario", "Citas", "Finalizadas", "Canceladas", "Pendientes"};
    }

    @Override
    public String[][] generar() {
        Empleado[] empleados = gestorempleados.getLista();
        Cita[] citas = gestorcitas.getLista();

        // Primero se cuentan los veterinarios para saber el tamaño de la tabla
        int cantidadvets = 0;
        for (int i = 0; i < empleados.length; i++) {
            if (empleados[i] instanceof Veterinario) {
                cantidadvets++;
            }
        }
        String[][] filas = new String[cantidadvets][6];
        int fila = 0;
        for (int i = 0; i < empleados.length; i++) {
            if (empleados[i] instanceof Veterinario) {
                int total = 0;
                int finalizadas = 0;
                int canceladas = 0;
                for (int j = 0; j < citas.length; j++) {
                    if (citas[j].getVeterinario() == empleados[i]) {
                        total++;
                        if (citas[j].getEstado().equals("Finalizada")) {
                            finalizadas++;
                        } else if (citas[j].getEstado().equals("Cancelada")) {
                            canceladas++;
                        }
                    }
                }
                filas[fila][0] = String.valueOf(empleados[i].getDni());
                filas[fila][1] = empleados[i].getNombreCompleto();
                filas[fila][2] = String.valueOf(total);
                filas[fila][3] = String.valueOf(finalizadas);
                filas[fila][4] = String.valueOf(canceladas);
                filas[fila][5] = String.valueOf(total - finalizadas - canceladas);
                fila++;
            }
        }
        return filas;
    }
}
