package view;

import service.Consultorio;
import utils.ConsoleUtils;
import model.domain.Paciente;

public class MenuColaView {
    private final Consultorio consultorio;

    public MenuColaView(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public void mostrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n=== MENÚ SALA DE ESPERA (COLA) ===");
            System.out.println("1. Registrar llegada de paciente (Encolar)");
            System.out.println("2. Atender siguiente paciente (Desencolar)");
            System.out.println("3. Consultar siguiente paciente (Frente)");
            System.out.println("4. Verificar si la fila está vacía");
            System.out.println("5. Contar pacientes en espera");
            System.out.println("6. Listar todos los pacientes en espera");
            System.out.println("0. Volver al menú principal");

            opcion = ConsoleUtils.leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> {
                    String id = ConsoleUtils.leerTexto("Ingrese identificación: ");
                    String nombre = ConsoleUtils.leerTexto("Ingrese nombre: ");
                    String telefono = ConsoleUtils.leerTexto("Ingrese teléfono: ");
                    int edad = ConsoleUtils.leerEntero("Ingrese edad: ");
                    String eps = ConsoleUtils.leerTexto("Ingrese EPS: ");

                    Paciente nuevoPaciente = new Paciente(id, nombre, telefono, edad, eps);
                    consultorio.registrarLlegadaPaciente(nuevoPaciente);
                    System.out.println("Paciente registrado en la cola de espera con éxito.");
                }
                case 2 -> {
                    if (consultorio.sinClientesEnEspera()) {
                        System.out.println("No hay clientes en espera.");
                    } else {
                        Paciente atendido = consultorio.atenderSiguiente();
                        System.out.println("Atendiendo a: " + atendido.getNombre() + " (ID: " + atendido.getIdentificacion() + ")");
                    }
                }
                case 3 -> {
                    if (consultorio.sinClientesEnEspera()) {
                        System.out.println("No hay clientes en espera.");
                    } else {
                        Paciente enTurno = consultorio.consultarSiguiente();
                        System.out.println("Siguiente paciente en turno: " + enTurno.getNombre() + " (ID: " + enTurno.getIdentificacion() + ")");
                    }
                }
                case 4 -> {
                    if (consultorio.sinClientesEnEspera()) {
                        System.out.println("La sala de espera está vacía.");
                    } else {
                        System.out.println("Hay pacientes esperando en la sala.");
                    }
                }
                case 5 -> System.out.println("Pacientes en espera: " + consultorio.contarEnEspera());
                case 6 -> System.out.println(consultorio.listarEnEspera());
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
}