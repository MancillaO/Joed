package Negocio;

import Datos.ConexionBD_joed;
import java.util.Scanner;

public class Menu_joed {

    // Colores
    private static final String RESET = "\033[0m";
    private static final String BLUE = "\033[34m";
    private static final String GREEN = "\033[32m";
    private static final String RED = "\033[31m";
    private static final String YELLOW = "\033[33m";
    private static final String CYAN = "\033[36m";

    private Scanner scanner;
    private ConexionBD_joed conexionBD;

    public Menu_joed() {
        this.scanner = new Scanner(System.in);
        this.conexionBD = new ConexionBD_joed();
    }

    // Mostrar menú principal
    public void mostrarMenu() {
        int opcion;
        for (;;) {
            // Diseño del menú
            System.out.println("\n" + BLUE + "╔════════════════════════════════╗");
            System.out.println("║       MENÚ DE AUTOLAVADO       ║");
            System.out.println("╠════════════════════════════════╣");
            System.out.println("║  1. Insertar Dueño             ║");
            System.out.println("║  2. Listar Dueños              ║");
            System.out.println("║  3. Insertar Coche             ║");
            System.out.println("║  4. Listar Coches              ║");
            System.out.println("║  5. Eliminar Dueño             ║");
            System.out.println("║  6. Eliminar Coche             ║");
            System.out.println("║  7. Actualizar Dueño           ║");
            System.out.println("║  8. Actualizar Coche           ║");
            System.out.println("║  9.  Salir                     ║");
            System.out.println("╚════════════════════════════════╝" + RESET);
            System.out.print(YELLOW + "Seleccione una opción: " + RESET);

            // Validación de entrada
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
            } else {
                System.out.println(RED + "❌ Entrada inválida. Introduzca un número." + RESET);
                scanner.nextLine();
                continue;
            }

            // Ejecutar la opción seleccionada
            switch (opcion) {
                case 1:
                    insertarDueno();
                    break;
                case 2:
                    listarDuenos();
                    break;
                case 3:
                    insertarCoche();
                    break;
                case 4:
                    listarCoches();
                    break;
                case 5:
                    eliminarDueno();
                    break;
                case 6:
                    eliminarCoche();
                    break;
                case 7:
                    actualizarDueno();
                    break;
                case 8:
                    actualizarCoche();
                    break;
                case 9:
                    System.out.println(GREEN + "👋 Gracias por usar el sistema de autolavado. ¡Hasta pronto!" + RESET);
                    return;
                default:
                    System.out.println(RED + "⚠ Opción inválida, intenta nuevamente." + RESET);
                    break;
            }
        }
    }

    private void insertarDueno() {
        System.out.print("Nombre del dueño: ");
        String nombre = scanner.nextLine();
        System.out.print("Email del dueño: ");
        String email = scanner.nextLine();
        conexionBD.insertDueno(nombre, email);
        System.out.println(GREEN + "✅ Dueño insertado correctamente." + RESET);
    }

    private void listarDuenos() {
        System.out.println(CYAN + "🔎 Listando dueños..." + RESET);
        conexionBD.listDuenos();
    }

    private void insertarCoche() {
        System.out.print("Marca del coche: ");
        String marca = scanner.nextLine();
        System.out.print("Modelo del coche: ");
        String modelo = scanner.nextLine();
        System.out.print("Matrícula del coche: ");
        String matricula = scanner.nextLine();
        System.out.print("ID del dueño: ");
        int duenoId = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        conexionBD.insertCoche(marca, modelo, matricula, duenoId);
        System.out.println(GREEN + "✅ Coche insertado correctamente." + RESET);
    }

    private void listarCoches() {
        System.out.println(CYAN + "🔎 Listando coches..." + RESET);
        conexionBD.listCoches();
    }

    private void eliminarDueno() {
        System.out.print("ID del dueño a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        conexionBD.deleteDueno(id);
    }

    private void eliminarCoche() {
        System.out.print("ID del coche a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        conexionBD.deleteCoche(id);
    }

    private void actualizarDueno() {
        System.out.print("ID del dueño a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        System.out.print("Nuevo nombre del dueño: ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Nuevo email del dueño: ");
        String nuevoEmail = scanner.nextLine();
        conexionBD.updateDueno(id, nuevoNombre, nuevoEmail);
        System.out.println(GREEN + "✅ Dueño actualizado correctamente." + RESET);
    }

    private void actualizarCoche() {
        System.out.print("ID del coche a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        System.out.print("Nueva marca del coche: ");
        String nuevaMarca = scanner.nextLine();
        System.out.print("Nuevo modelo del coche: ");
        String nuevoModelo = scanner.nextLine();
        System.out.print("Nueva matrícula del coche: ");
        String nuevaMatricula = scanner.nextLine();
        System.out.print("Nuevo ID del dueño: ");
        int nuevoDuenoId = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        conexionBD.updateCoche(id, nuevaMarca, nuevoModelo, nuevaMatricula, nuevoDuenoId);
        System.out.println(GREEN + "✅ Coche actualizado correctamente." + RESET);
    }
}