package view;

import service.PacienteService;
import utils.ConsoleUtils;

public class MenuPacientesView {

    private final PacienteService pacienteService;

    public MenuPacientesView(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = ConsoleUtils.leerEntero("Elija una opción: ");
            switch (opcion) {
                case 1 -> registrar();
                case 2 -> System.out.println(pacienteService.listarPacientes());
                case 3 -> System.out.println("Pacientes registrados: " + pacienteService.contarPacientes());
                case 4 -> guardarTodo();
                case 0 -> guardarTodo();
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    private void mostrarOpciones() {
        System.out.println("\n--- MENÚ PACIENTES ---");
        System.out.println("1. Registrar paciente");
        System.out.println("2. Listar pacientes");
        System.out.println("3. Contar pacientes");
        System.out.println("4. Guardar");
        System.out.println("0. Guardar y volver");
    }

    private void registrar() {
        String identificacion = ConsoleUtils.leerTexto("Identificación del paciente: ");
        if (identificacion.trim().isEmpty()) {
            System.out.println("La identificación no puede estar vacía.");
            return;
        }
        if (pacienteService.existePaciente(identificacion)) {
            System.out.println("Ya existe un paciente con esa identificación.");
            return;
        }
        String nombre = ConsoleUtils.leerTexto("Nombre: ");
        String telefono = ConsoleUtils.leerTexto("Teléfono: ");
        String fecha = ConsoleUtils.leerTexto("Fecha de nacimiento (yyyy-MM-dd): ");
        while (!pacienteService.fechaValida(fecha)) {
            System.out.println("Fecha inválida. Use el formato yyyy-MM-dd.");
            fecha = ConsoleUtils.leerTexto("Fecha de nacimiento (yyyy-MM-dd): ");
        }
        String tipoSangre = ConsoleUtils.leerTexto("Tipo de sangre: ");
        pacienteService.registrarPaciente(identificacion, nombre, telefono, fecha, tipoSangre);
        System.out.println("Paciente registrado con éxito.");
    }

    private void guardarTodo() {
        System.out.println(pacienteService.guardarPacientes());
    }
}