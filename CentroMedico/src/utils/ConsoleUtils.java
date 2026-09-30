package utils;

import java.util.Scanner;

public class ConsoleUtils {
    // Creamos un único Scanner estático para todo el programa
    private static Scanner scanner = new Scanner(System.in);

    // Método para leer texto
    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    public static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.println("Error: Por favor ingresa un número válido.");
            System.out.print(mensaje);
            scanner.next(); // Limpiar el buffer
        }
        int numero = scanner.nextInt();
        scanner.nextLine(); // Limpiar el salto de línea que queda después del número
        return numero;
    }
}