package view;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import model.domain.Cita;
import model.domain.Consulta;
import model.domain.Medico;
import model.domain.Paciente;
import service.AgendaService;
import service.Consultorio;
import service.ConsultorioPila;
import service.MedicoService;
import service.PacienteService;
import utils.ConsoleUtils;

public final class Menu {
    // Services con persistencia: se cargan solos al crearse
    private final PacienteService pacienteService;
    private final MedicoService medicoService;
    private final AgendaService agendaService;

    private final Consultorio service;
    private final ConsultorioPila consultorioPila;
    private final Medico medicoActual;
    private final MenuPilaView menuPila;
    private final MenuColaView menuCola;
    private final MenuAgendaView menuAgenda;

    public Menu() {
        // Orden importa: pacientes y medicos primero, luego la agenda que los enlaza
        this.pacienteService = new PacienteService();
        this.medicoService = new MedicoService();
        this.agendaService = new AgendaService(pacienteService, medicoService);

        Paciente paciente = elegirPaciente();
        this.medicoActual = elegirMedico();
        this.service = new Consultorio(paciente);
        this.consultorioPila = new ConsultorioPila(paciente);
        this.menuPila = new MenuPilaView(consultorioPila);
        this.menuCola = new MenuColaView(service);
        this.menuAgenda = new MenuAgendaView(agendaService);
    }

    // Si la identificacion ya existe (cargada del archivo) la usa; si no, la registra
    private Paciente elegirPaciente() {
        System.out.println("===========Antes de continuar identifique un paciente===========");
        Paciente paciente = null;
        while (paciente == null) {
            try {
                String id = ConsoleUtils.leerTexto("Ingrese la identificacion del paciente: ");
                paciente = pacienteService.buscarPaciente(id);
                if (paciente != null) {
                    System.out.println("Paciente encontrado: " + paciente.getNombre());
                } else {
                    String nombre = ConsoleUtils.leerTexto("Ingrese el nombre del paciente: ");
                    String telefono = ConsoleUtils.leerTexto("Ingrese el telefono del paciente: ");
                    LocalDate fechaNacimiento = leerFecha("Ingrese la fecha de nacimiento (yyyy-MM-dd): ");
                    String tipoSangre = ConsoleUtils.leerTexto("Ingrese el tipo de sangre: ");
                    pacienteService.registrarPaciente(id, nombre, telefono, fechaNacimiento, tipoSangre);
                    paciente = pacienteService.buscarPaciente(id);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Dato inválido: " + e.getMessage());
            }
        }
        return paciente;
    }

    private Medico elegirMedico() {
        System.out.println("===========Antes de continuar identifique un medico===========");
        Medico medico = null;
        while (medico == null) {
            try {
                String id = ConsoleUtils.leerTexto("Ingrese la identificacion del medico: ");
                medico = medicoService.buscarMedico(id);
                if (medico != null) {
                    System.out.println("Médico encontrado: " + medico.getNombre());
                } else {
                    String nombre = ConsoleUtils.leerTexto("Ingrese el nombre del medico: ");
                    String telefono = ConsoleUtils.leerTexto("Ingrese el telefono del medico: ");
                    String especialidad = ConsoleUtils.leerTexto("Ingrese la especialidad del medico (especialidad en numero): ");
                    String registro = ConsoleUtils.leerTexto("Ingrese el registro del medico: ");
                    medicoService.registrarMedico(id, nombre, telefono, especialidad, registro);
                    medico = medicoService.buscarMedico(id);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Dato inválido: " + e.getMessage());
            }
        }
        return medico;
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

    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Ingresar como Paciente (Gestión de Citas)");
            System.out.println("2. Ingresar como Médico (Gestión de Consultas)");
            System.out.println("3. Menu De Consultas (Pila)");
            System.out.println("4. Menu Sala de Espera (Cola)");
            System.out.println("5. Menu Agenda (Lista Doble)");
            System.out.println("6. Guardar / cargar archivos");
            System.out.println("0. Salir");

            opcion = ConsoleUtils.leerEntero("Seleccione un rol: ");

            switch (opcion) {
                case 1 -> submenuPaciente();
                case 2 -> submenuMedico();
                case 3 -> menuPila.iniciar();
                case 4 -> menuCola.mostrarMenu();
                case 5 -> menuAgenda.iniciar();
                case 6 -> submenuArchivos();
                case 0 -> {
                    guardarTodo();
                    System.out.println("Saliendo del sistema...");
                }
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private void guardarTodo() {
        System.out.println(pacienteService.guardarPacientes());
        System.out.println(medicoService.guardarMedicos());
        System.out.println(agendaService.guardarAgenda());
    }

    private void submenuArchivos() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- ARCHIVOS (data/) ---");
            System.out.println("1. Guardar todo (pacientes, médicos y agenda)");
            System.out.println("2. Cargar todo desde los archivos");
            System.out.println("0. Volver al Menú Principal");

            opcion = ConsoleUtils.leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> guardarTodo();
                case 2 -> {
                    // mismo orden que al arrancar: la agenda se enlaza con lo ya cargado
                    System.out.println(pacienteService.cargarPacientes());
                    System.out.println(medicoService.cargarMedicos());
                    System.out.println(agendaService.cargarAgenda());
                }
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void submenuPaciente() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENÚ PACIENTE (CITAS) ---");
            System.out.println("1. Agregar Cita");
            System.out.println("2. Listar Citas");
            System.out.println("3. Actualizar Cita");
            System.out.println("4. Eliminar Cita");
            System.out.println("0. Volver al Menú Principal");

            opcion = ConsoleUtils.leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> {
                    String hora = ConsoleUtils.leerTexto("Hora de la cita (Ej. 10:00 AM): ");
                    String motivo = ConsoleUtils.leerTexto("Motivo: ");
                    service.agregarCita(new Cita(LocalDate.now(), hora, motivo, null, medicoActual));
                    System.out.println("Cita agregada con éxito.");
                }
                case 2 -> mostrarCitas();
                case 3 -> {
                    mostrarCitas();
                    if (service.listarCitas().getTamano() > 0) {
                        int indiceActualizar = ConsoleUtils.leerEntero("Ingrese el número de la cita a actualizar: ") - 1;
                        String nuevaHora = ConsoleUtils.leerTexto("Nueva hora (Ej. 11:30 AM): ");
                        String nuevoMotivo = ConsoleUtils.leerTexto("Nuevo motivo: ");

                        if (service.actualizarCita(indiceActualizar, nuevaHora, nuevoMotivo, medicoActual)) {
                            System.out.println("Cita actualizada exitosamente.");
                        } else {
                            System.out.println("Error: No se encontró la cita.");
                        }
                    }
                }
                case 4 -> {
                    mostrarCitas();
                    if (service.listarCitas().getTamano() > 0) {
                        int indiceEliminar = ConsoleUtils.leerEntero("Ingrese el número de la cita a eliminar: ") - 1;

                        if (service.eliminarCita(indiceEliminar)) {
                            System.out.println("Cita eliminada exitosamente.");
                        } else {
                            System.out.println("Error: No se pudo eliminar la cita.");
                        }
                    }
                }
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void submenuMedico() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENÚ MÉDICO (CONSULTAS) ---");
            System.out.println("1. Registrar Consulta");
            System.out.println("2. Retirar Última Consulta");
            System.out.println("3. Ver Última Consulta");
            System.out.println("4. Contar Consultas");
            System.out.println("0. Volver al Menú Principal");

            opcion = ConsoleUtils.leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> {
                    String motivoCons = ConsoleUtils.leerTexto("Motivo de consulta: ");
                    String diag = ConsoleUtils.leerTexto("Diagnóstico: ");
                    String trat = ConsoleUtils.leerTexto("Tratamiento: ");
                    consultorioPila.registrarConsulta(motivoCons, diag, trat, LocalDate.now());
                    System.out.println("Consulta registrada con éxito.");
                }
                case 2 -> {
                    if (consultorioPila.sinConsultasPorRetirar()) {
                        System.out.println("No hay consultas por retirar.");
                    } else {
                        Consulta retirada = consultorioPila.retirarUltimaConsulta();
                        System.out.println("Consulta retirada:");
                        imprimirConsulta(retirada);
                    }
                }
                case 3 -> {
                    if (consultorioPila.sinConsultasPorRetirar()) {
                        System.out.println("No hay consultas registradas.");
                    } else {
                        System.out.println("Última consulta registrada:");
                        imprimirConsulta(consultorioPila.consultarUltimaConsulta());
                    }
                }
                case 4 -> System.out.println("Consultas registradas: " + consultorioPila.contarConsultas());
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void mostrarCitas() {
        System.out.println("\n--- Lista de Citas ---");
        int tamanoCitas = service.listarCitas().getTamano();
        if (tamanoCitas == 0) {
            System.out.println("No hay citas registradas.");
        } else {
            for (int i = 0; i < tamanoCitas; i++) {
                Cita c = service.listarCitas().obtener(i);
                System.out.println((i + 1) + ". Hora: " + c.getHora() + " | Motivo: " + c.getMotivo());
            }
        }
    }

    private void imprimirConsulta(Consulta c) {
        System.out.println("Motivo: " + c.getMotivo()
                + " | Diagnóstico: " + c.getDiagnostico()
                + " | Tratamiento: " + c.getTratamiento());
    }
}