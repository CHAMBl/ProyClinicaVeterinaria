/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyclinicaveterinaria;

import java.util.Scanner;

/**
 *
 * @author josec
 */
public class ProyClinicaVeterinaria {

    /**
     * @param args the command line arguments
     */
    static Scanner sc = new Scanner(System.in);
    static GestorEmpleados gestorEmpleados = new GestorEmpleados(50);
    static GestorClientes gestorClientes = new GestorClientes(100);
    static Empleado usuarioActual;

    public static void main(String[] args) {
        Administrador admin = new Administrador("Admin", "Principal", 12345678);
        admin.setUsuario("admin");
        admin.setContraseña("admin123");
        admin.setRol("Administrador");
        gestorEmpleados.registrar(admin);

        int opcion;
        do {
            System.out.println("\n=== CLÍNICA VETERINARIA ===");
            System.out.println("1. Iniciar sesion");
            System.out.println("0. Salir");
            opcion = leerEntero("Opcion: ");
            if (opcion == 1) {
                iniciarSesion();
            }
        } while (opcion != 0);
        System.out.println("Hasta pronto.");
    }

    static void iniciarSesion() {
        String usuario = leerTexto("Usuario: ");
        String contraseña = leerTexto("Contraseña: ");
        usuarioActual = gestorEmpleados.autenticar(usuario, contraseña);
        if (usuarioActual == null) {
            System.out.println("Usuario o contraseña incorrectos.");
            return;
        }
        System.out.println("Bienvenido, " + usuarioActual.getNombreCompleto() + " (" + usuarioActual.getRol() + ")");
        if (usuarioActual instanceof Administrador) {
            menuAdministrador();
        } else if (usuarioActual instanceof Recepcionista) {
            menuRecepcionista();
        } else if (usuarioActual instanceof Veterinario) {
            menuVeterinario();
        }
        usuarioActual = null;
    }

    static void menuAdministrador() {
        int opcion;
        do {
            System.out.println("\n--- MENU ADMINISTRADOR ---");
            System.out.println("1. Gestionar empleados");
            System.out.println("2. Gestionar clientes");
            System.out.println("0. Cerrar sesion");
            opcion = leerEntero("Opcion: ");
            switch (opcion) {
                case 1:
                    menuEmpleados();
                    break;
                case 2:
                    menuClientes();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    static void menuRecepcionista() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ RECEPCIONISTA ---");
            System.out.println("1. Gestionar clientes");
            System.out.println("0. Cerrar sesion");
            opcion = leerEntero("Opcion: ");
            switch (opcion) {
                case 1:
                    menuClientes();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no valida.");
            }
        } while (opcion != 0);
    }

    static void menuVeterinario() {
        int opcion;
        do {
            System.out.println("\n--- MENU VETERINARIO ---");
            System.out.println("1. Consultar clientes");
            System.out.println("2. Cambiar mi disponibilidad");
            System.out.println("0. Cerrar sesión");
            opcion = leerEntero("Opción: ");
            switch (opcion) {
                case 1:
                    gestorClientes.listar();
                    break;
                case 2:
                    cambiarDisponibilidad();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    static void cambiarDisponibilidad() {
        boolean nuevo = !usuarioActual.estaDisponible();
        usuarioActual.setDisponible(nuevo);
        System.out.println("Ahora su disponibilidad es: " + (nuevo ? "Disponible" : "No disponible"));
    }

    static void menuEmpleados() {
        int opcion;
        do {
            System.out.println("\n--- GESTION DE EMPLEADOS ---");
            System.out.println("1. Registrar empleado");
            System.out.println("2. Modificar empleado");
            System.out.println("3. Eliminar empleado");
            System.out.println("4. Listar empleados");
            System.out.println("0. Volver");
            opcion = leerEntero("Opcion: ");
            switch (opcion) {
                case 1:
                    registrarEmpleado();
                    break;
                case 2:
                    modificarEmpleado();
                    break;
                case 3:
                    eliminarEmpleado();
                    break;
                case 4:
                    gestorEmpleados.listar();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion no válida.");
            }
        } while (opcion != 0);
    }

    static void registrarEmpleado() {
        if (gestorEmpleados.estaLleno()) {
            System.out.println("No hay espacio para más empleados.");
            return;
        }
        System.out.println("Rol del empleado: 1. Administrador  2. Recepcionista  3. Veterinario");
        int rol = leerEntero("Rol: ");
        if (rol < 1 || rol > 3) {
            System.out.println("Rol no valido.");
            return;
        }
        int dni = leerDni("DNI (8 dígitos): ");
        if (gestorEmpleados.existeDni(dni, null)) {
            System.out.println("Ya existe un empleado con ese DNI.");
            return;
        }
        String nombres = leerTexto("Nombres: ");
        String apellidos = leerTexto("Apellidos: ");
        String usuario = leerTexto("Usuario: ");
        if (gestorEmpleados.existeUsuario(usuario, null)) {
            System.out.println("Ese usuario ya está en uso.");
            return;
        }
        String contraseña = leerTexto("Contraseña: ");

        Empleado nuevo;
        String nombreRol;
        if (rol == 1) {
            nuevo = new Administrador(nombres, apellidos, dni);
            nombreRol = "Administrador";
        } else if (rol == 2) {
            nuevo = new Recepcionista(nombres, apellidos, dni);
            nombreRol = "Recepcionista";
        } else {
            nuevo = new Veterinario(true, nombres, apellidos, dni);
            nombreRol = "Veterinario";
        }
        nuevo.setUsuario(usuario);
        nuevo.setContraseña(contraseña);
        nuevo.setRol(nombreRol);

        if (gestorEmpleados.registrar(nuevo)) {
            System.out.println("Empleado registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el empleado.");
        }
    }

    static void modificarEmpleado() {
        int dni = leerDni("DNI del empleado a modificar: ");
        Empleado e = gestorEmpleados.buscarPorDni(dni);
        if (e == null) {
            System.out.println("No existe un empleado con ese DNI.");
            return;
        }
        System.out.println("Datos actuales:");
        e.mostrarDatos();
        System.out.println("Deje vacio para mantener el valor actual.");

        String nombres = leerOpcional("Nuevos nombres: ");
        if (!nombres.isEmpty()) {
            e.setNombres(nombres);
        }
        String apellidos = leerOpcional("Nuevos apellidos: ");
        if (!apellidos.isEmpty()) {
            e.setApellidos(apellidos);
        }
        String dniTxt = leerOpcional("Nuevo DNI (8 dígitos): ");
        if (!dniTxt.isEmpty()) {
            if (!esDniValido(dniTxt)) {
                System.out.println("DNI no válido, se mantiene el anterior.");
            } else {
                int nuevoDni = Integer.parseInt(dniTxt);
                if (gestorEmpleados.existeDni(nuevoDni, e)) {
                    System.out.println("Ese DNI ya pertenece a otro empleado, se mantiene el anterior.");
                } else {
                    e.setDni(nuevoDni);
                }
            }
        }
        String usuario = leerOpcional("Nuevo usuario: ");
        if (!usuario.isEmpty()) {
            if (gestorEmpleados.existeUsuario(usuario, e)) {
                System.out.println("Ese usuario ya esta en uso, se mantiene el anterior.");
            } else {
                e.setUsuario(usuario);
            }
        }
        String contraseña = leerOpcional("Nueva contraseña: ");
        if (!contraseña.isEmpty()) {
            e.setContraseña(contraseña);
        }
        if (e instanceof Veterinario) {
            String disp = leerOpcional("¿Disponible? (S/N): ");
            if (disp.equalsIgnoreCase("S")) {
                e.setDisponible(true);
            } else if (disp.equalsIgnoreCase("N")) {
                e.setDisponible(false);
            }
        }
        System.out.println("Empleado modificado correctamente.");
    }

    static void eliminarEmpleado() {
        int dni = leerDni("DNI del empleado a eliminar: ");
        Empleado e = gestorEmpleados.buscarPorDni(dni);
        if (e == null) {
            System.out.println("No existe un empleado con ese DNI.");
            return;
        }
        if (e == usuarioActual) {
            System.out.println("No puede eliminar su propia cuenta mientras esta en sesion.");
            return;
        }
        e.mostrarDatos();
        if (confirmar("¿Seguro que desea eliminarlo? (S/N): ")) {
            gestorEmpleados.eliminar(dni);
            System.out.println("Empleado eliminado.");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    static void menuClientes() {
        int opcion;
        do {
            System.out.println("\n--- GESTION DE CLIENTES ---");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Modificar cliente");
            System.out.println("3. Eliminar cliente");
            System.out.println("4. Listar clientes");
            System.out.println("0. Volver");
            opcion = leerEntero("Opción: ");
            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    modificarCliente();
                    break;
                case 3:
                    eliminarCliente();
                    break;
                case 4:
                    gestorClientes.listar();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    static void registrarCliente() {
        if (gestorClientes.estaLleno()) {
            System.out.println("No hay espacio para más clientes.");
            return;
        }
        int dni = leerDni("DNI (8 dígitos): ");
        if (gestorClientes.existeDni(dni, null)) {
            System.out.println("Ya existe un cliente con ese DNI.");
            return;
        }
        String nombres = leerTexto("Nombres: ");
        String apellidos = leerTexto("Apellidos: ");
        String telefono = leerTexto("Teléfono: ");
        String correo = leerTexto("Correo: ");
        String direccion = leerTexto("Dirección: ");

        Cliente c = new Cliente(telefono, correo, direccion, nombres, apellidos, dni);
        if (gestorClientes.registrar(c)) {
            System.out.println("Cliente registrado correctamente.");
        } else {
            System.out.println("No se pudo registrar el cliente.");
        }
    }

    static void modificarCliente() {
        int dni = leerDni("DNI del cliente a modificar: ");
        Cliente c = gestorClientes.buscarPorDni(dni);
        if (c == null) {
            System.out.println("No existe un cliente con ese DNI.");
            return;
        }
        System.out.println("Datos actuales:");
        c.mostrarDatos();
        System.out.println("Deje vacío para mantener el valor actual.");

        String nombres = leerOpcional("Nuevos nombres: ");
        if (!nombres.isEmpty()) {
            c.setNombres(nombres);
        }
        String apellidos = leerOpcional("Nuevos apellidos: ");
        if (!apellidos.isEmpty()) {
            c.setApellidos(apellidos);
        }
        String dniTxt = leerOpcional("Nuevo DNI (8 dígitos): ");
        if (!dniTxt.isEmpty()) {
            if (!esDniValido(dniTxt)) {
                System.out.println("DNI no valido, se mantiene el anterior.");
            } else {
                int nuevoDni = Integer.parseInt(dniTxt);
                if (gestorClientes.existeDni(nuevoDni, c)) {
                    System.out.println("Ese DNI ya pertenece a otro cliente, se mantiene el anterior.");
                } else {
                    c.setDni(nuevoDni);
                }
            }
        }
        String telefono = leerOpcional("Nuevo telefono: ");
        if (!telefono.isEmpty()) {
            c.setTelefono(telefono);
        }
        String correo = leerOpcional("Nuevo correo: ");
        if (!correo.isEmpty()) {
            c.setCorreo(correo);
        }
        String direccion = leerOpcional("Nueva direccion: ");
        if (!direccion.isEmpty()) {
            c.setDireccion(direccion);
        }
        System.out.println("Cliente modificado correctamente.");
    }

    static void eliminarCliente() {
        int dni = leerDni("DNI del cliente a eliminar: ");
        Cliente c = gestorClientes.buscarPorDni(dni);
        if (c == null) {
            System.out.println("No existe un cliente con ese DNI.");
            return;
        }
        c.mostrarDatos();
        if (confirmar("¿Seguro que desea eliminarlo? (S/N): ")) {
            gestorClientes.eliminar(dni);
            System.out.println("Cliente eliminado.");
        } else {
            System.out.println("Operacion cancelada.");
        }
    }

    static String leerTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = sc.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("Este campo no puede estar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    static String leerOpcional(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un numero válido.");
            }
        }
    }

    static boolean esDniValido(String texto) {
        return texto.matches("\\d{8}");
    }

    static int leerDni(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = sc.nextLine().trim();
            if (esDniValido(texto)) {
                return Integer.parseInt(texto);
            }
            System.out.println("El DNI debe tener exactamente 8 digitos.");
        }
    }

    static boolean confirmar(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim().equalsIgnoreCase("S");
    }    
}
