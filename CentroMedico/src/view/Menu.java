package view;

import java.time.LocalDate;
import model.domain.Cita;
import model.domain.Consulta;
import model.domain.Medico;
import model.domain.Paciente;
import service.Consultorio;
import service.ConsultorioPila; 
import utils.ConsoleUtils; 

public final class Menu {
    private final Consultorio service;
    private final ConsultorioPila consultorioPila; 
    private final Medico medicoMock;
    private final MenuPilaView menuPila;
    private final MenuColaView menuCola;

    public Menu() {
        Paciente paciente = menuPaciente();
        this.service = new Consultorio(paciente);
        this.consultorioPila = new ConsultorioPila(paciente);  
        this.medicoMock = menuMedico();
        this.menuPila = new MenuPilaView(consultorioPila);      
        this.menuCola = new MenuColaView(service); 
    }

    public Medico menuMedico() {
        System.out.println("===========Antes de continuar debe registrar un medico===========");
        String identificacionMedico = ConsoleUtils.leerTexto("Ingrese la identificacion del medico: ");
        String nombreMedico = ConsoleUtils.leerTexto("Ingrese el nombre del medico: ");
        String telefonoMedico = ConsoleUtils.leerTexto("Ingrese el telefono del medico: ");
        String especialidadMedico = ConsoleUtils.leerTexto("Ingrese la especialidad del medico (especialidad en numero): ");
        String numeroRegistroMedico = ConsoleUtils.leerTexto("Ingrese el registro del medico: ");
        return new Medico(identificacionMedico, nombreMedico, telefonoMedico, especialidadMedico, numeroRegistroMedico);
    }

    public Paciente menuPaciente() {
        System.out.println("===========Antes de continuar debe registrar un paciente===========");
        String identificacionPaciente = ConsoleUtils.leerTexto("Ingrese la identificacion del paciente: ");
        String nombrePaciente = ConsoleUtils.leerTexto("Ingrese el nombre del paciente: ");
        String telefonoPaciente = ConsoleUtils.leerTexto("Ingrese el telefono del paciente: ");
        int edadPaciente = ConsoleUtils.leerEntero("Ingrese la edad del paciente: ");
        String epsPaciente = ConsoleUtils.leerTexto("Ingrese la eps del paciente: ");
        return new Paciente(identificacionPaciente, nombrePaciente, telefonoPaciente, edadPaciente, epsPaciente);
    }

    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Ingresar como Paciente (Gestión de Citas)");
            System.out.println("2. Ingresar como Médico (Gestión de Consultas)");
            System.out.println("3. Menu De Consultas (Pila)");
            System.out.println("4. Menu Sala de Espera (Cola)");
            System.out.println("0. Salir");
            
            opcion = ConsoleUtils.leerEntero("Seleccione un rol: "); 

            switch (opcion) {
                case 1 -> submenuPaciente();
                case 2 -> submenuMedico();
                case 3 -> menuPila.iniciar();
                case 4 -> menuCola.mostrarMenu(); 
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
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
                    service.agregarCita(new Cita(LocalDate.now(), hora, motivo, null, medicoMock));
                    System.out.println("Cita agregada con éxito.");
                }
                case 2 -> mostrarCitas();
                case 3 -> {
                    mostrarCitas();
                    if (service.listarCitas().getTamano() > 0) {
                        int indiceActualizar = ConsoleUtils.leerEntero("Ingrese el número de la cita a actualizar: ") - 1;
                        String nuevaHora = ConsoleUtils.leerTexto("Nueva hora (Ej. 11:30 AM): ");
                        String nuevoMotivo = ConsoleUtils.leerTexto("Nuevo motivo: ");
                        
                        if (service.actualizarCita(indiceActualizar, nuevaHora, nuevoMotivo, medicoMock)) {
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