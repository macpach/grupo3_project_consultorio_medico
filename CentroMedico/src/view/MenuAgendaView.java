package view;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import model.domain.Cita;
import service.AgendaService;
import utils.ConsoleUtils;

public final class MenuAgendaView {

    private final AgendaService service;

    public MenuAgendaView(AgendaService service) {
        this.service = service;
    }

    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENÚ AGENDA (LISTA DOBLE) ---");
            System.out.println("1. Registrar cita");
            System.out.println("2. Listar citas en orden");
            System.out.println("3. Listar citas en orden inverso");
            System.out.println("4. Ir a la última cita");
            System.out.println("5. Ver cita anterior");
            System.out.println("6. Ver cita siguiente");
            System.out.println("7. Cancelar la cita que se está mirando");
            System.out.println("8. Contar citas");
            System.out.println("9. Guardar agenda en archivo");
            System.out.println("10. Cargar agenda desde archivo");
            System.out.println("0. Volver al Menú Principal");

            opcion = ConsoleUtils.leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> registrar();
                    case 2 -> System.out.println(service.listarCitasEnOrden());
                    case 3 -> System.out.println(service.listarCitasEnOrdenInverso());
                    case 4 -> imprimir("Mirando", service.irAUltimaCita());
                    case 5 -> imprimir("Cita anterior", service.verCitaAnterior());
                    case 6 -> imprimir("Cita siguiente", service.verCitaSiguiente());
                    case 7 -> imprimir("Cita cancelada", service.cancelarCita());
                    case 8 -> System.out.println("Citas en la agenda: " + service.contarCitas());
                    case 9 -> System.out.println(service.guardarAgenda());
                    case 10 -> System.out.println(service.cargarAgenda());
                    case 0 -> System.out.println("Regresando al menú principal...");
                    default -> System.out.println("Opción inválida.");
                }
            } catch (NoSuchElementException | IllegalArgumentException e) {
                System.out.println("Aviso: " + e.getMessage());
            }
        }
    }

    private void registrar() {
        String idPaciente = ConsoleUtils.leerTexto("Identificación del paciente: ");
        String idMedico = ConsoleUtils.leerTexto("Identificación del médico: ");
        LocalDate fecha = leerFecha("Fecha de la cita (yyyy-MM-dd): ");
        String hora = ConsoleUtils.leerTexto("Hora de la cita (Ej. 10:00 AM): ");
        String motivo = ConsoleUtils.leerTexto("Motivo: ");
        service.registrarCita(idPaciente, idMedico, fecha, hora, motivo);
        System.out.println("Cita registrada con éxito.");
    }

    private LocalDate leerFecha(String mensaje) {
        while (true) {
            try {
                return LocalDate.parse(ConsoleUtils.leerTexto(mensaje));
            } catch (DateTimeParseException e) {
                System.out.println("Fecha inválida. Use el formato yyyy-MM-dd.");
            }
        }
    }

    private void imprimir(String titulo, Cita cita) {
        if (cita == null) {
            System.out.println("No hay citas en la agenda.");
        } else {
            System.out.println(titulo + ": " + cita);
        }
    }
}
