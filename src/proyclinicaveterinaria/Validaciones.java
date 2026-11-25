/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyclinicaveterinaria;

/**
 *
 * @author silver
 */
public class Validaciones {
    
    // true si el texto tiene formato dd/mm/aaaa y es una fecha que existe
    public boolean esFechaValida(String fecha) {
        if (!fecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return false;
        }
        int dia = Integer.valueOf(fecha.substring(0, 2));
        int mes = Integer.valueOf(fecha.substring(3, 5));
        int anio = Integer.valueOf(fecha.substring(6, 10));
        if (mes < 1 || mes > 12 || dia < 1 || anio < 2000) {
            return false;
        }
        return dia <= diasDelMes(mes, anio);
    }

    private int diasDelMes(int mes, int anio) {
        if (mes == 2) {
            // año bisiesto: divisible entre 4 y (no entre 100, o sí entre 400)
            if (anio % 400 == 0 || (anio % 4 == 0 && anio % 100 != 0)) {
                return 29;
            }
            return 28;
        }
        if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
            return 30;
        }
        return 31;
    }

    // Convierte "25/11/2026" en el número 20261125, para poder comparar fechas con < y >
    public int fechaANumero(String fecha) {
        int dia = Integer.valueOf(fecha.substring(0, 2));
        int mes = Integer.valueOf(fecha.substring(3, 5));
        int anio = Integer.valueOf(fecha.substring(6, 10));
        return anio * 10000 + mes * 100 + dia;
    }

    // true si el texto tiene formato hh:mm (24 horas) y es una hora que existe
    public boolean esHoraValida(String hora) {
        if (!hora.matches("\\d{2}:\\d{2}")) {
            return false;
        }
        int horas = Integer.valueOf(hora.substring(0, 2));
        int minutos = Integer.valueOf(hora.substring(3, 5));
        return horas < 24 && minutos < 60;
    }

    // Convierte "09:30" en 570 (minutos desde la medianoche), para sumar duraciones
    public int horaAMinutos(String hora) {
        int horas = Integer.valueOf(hora.substring(0, 2));
        int minutos = Integer.valueOf(hora.substring(3, 5));
        return horas * 60 + minutos;
    }
}


