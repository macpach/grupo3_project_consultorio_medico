package model.domain;

import java.time.LocalDate;
import java.util.Scanner;
import model.domain.structures.Consultorio;

public class Menu {
    private Consultorio service;
    private Scanner scanner;
    private Medico medicoMock; 

    public Menu(Consultorio service) {
        Paciente paciente = menuPaciente();     
        this.service = new Consultorio(paciente);
        this.scanner = new Scanner(System.in);
        this.medicoMock = menuMedico();
    }

    public Paciente menuMedico() {
        // TO-do: Adaptar el metodo para crear un medico
        System.out.println("Antes de continuar debe registrar un medico");
        System.out.println("Ingrese la identificacion del paciente: ");
        String identificacionPaciente = scanner.nextLine();
        System.out.println("Ingrese el nombre del paciente: ");
        String nombrePaciente = scanner.nextLine();
        System.out.println("Ingrese el telefono del paciente: ");
        String telefonoPaciente = scanner.nextLine();
        System.out.println("Ingrese la edad del paciente: ");
        int edadPaciente = Integer.parseInt(scanner.nextLine());
        System.out.println("Ingrese la eps del paciente: ");
        String epsPaciente = scanner.nextLine();
        Medico medico = new Medico(identificacionPaciente, nombrePaciente, telefonoPaciente, edadPaciente, epsPaciente);
        return medico;
    }

    public Paciente menuPaciente() {
        System.out.println("Antes de continuar debe registrar un paciente");
        System.out.println("Ingrese la identificacion del paciente: ");
        String identificacionPaciente = scanner.nextLine();
        System.out.println("Ingrese el nombre del paciente: ");
        String nombrePaciente = scanner.nextLine();
        System.out.println("Ingrese el telefono del paciente: ");
        String telefonoPaciente = scanner.nextLine();
        System.out.println("Ingrese la edad del paciente: ");
        int edadPaciente = Integer.parseInt(scanner.nextLine());
        System.out.println("Ingrese la eps del paciente: ");
        String epsPaciente = scanner.nextLine();
        Paciente paciente = new Paciente(identificacionPaciente, nombrePaciente, telefonoPaciente, edadPaciente, epsPaciente);
        return paciente;
    }

    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENÚ CONSULTORIO MÉDICO ---");
            System.out.println("1. Agregar Cita");
            System.out.println("2. Listar Citas");
            System.out.println("3. Actualizar Cita");
            System.out.println("4. Eliminar Cita");
            System.out.println("5. Agregar Consulta");
            System.out.println("6. Listar Consultas");
            System.out.println("7. Actualizar Consulta");
            System.out.println("8. Eliminar Consulta");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            
            try { 
                opcion = Integer.parseInt(scanner.nextLine()); 
            } catch (NumberFormatException e) { 
                opcion = -1; 
            }

            switch (opcion) {
                case 1:
                    System.out.print("Hora de la cita (Ej. 10:00 AM): ");
                    String hora = scanner.nextLine();
                    System.out.print("Motivo: ");
                    String motivo = scanner.nextLine();
                    service.agregarCita(new Cita(LocalDate.now(), hora, motivo, null, medicoMock));
                    System.out.println("Cita agregada con éxito.");
                    break;
                case 2:
                    mostrarCitas();
                    break;
                case 3:
                    mostrarCitas();
                    if (service.listarCitas().getTamano() > 0) {
                        System.out.print("Ingrese el número de la cita a actualizar: ");
                        int indiceActualizar = Integer.parseInt(scanner.nextLine()) - 1;
                        System.out.print("Nueva hora (Ej. 11:30 AM): ");
                        String nuevaHora = scanner.nextLine();
                        System.out.print("Nuevo motivo: ");
                        String nuevoMotivo = scanner.nextLine();
                        
                        if (service.actualizarCita(indiceActualizar, nuevaHora, nuevoMotivo, medicoMock)) {
                            System.out.println("Cita actualizada exitosamente.");
                        } else {
                            System.out.println("Error: No se encontró la cita.");
                        }
                    }
                    break;
                case 4:
                    mostrarCitas();
                    if (service.listarCitas().getTamano() > 0) {
                        System.out.print("Ingrese el número de la cita a eliminar: ");
                        int indiceEliminar = Integer.parseInt(scanner.nextLine()) - 1;
                        
                        if (service.eliminarCita(indiceEliminar)) {
                            System.out.println("Cita eliminada exitosamente.");
                        } else {
                            System.out.println("Error: No se pudo eliminar la cita.");
                        }
                    }
                    break;
                case 5:
                    System.out.print("Motivo de consulta: ");
                    String motivoCons = scanner.nextLine();
                    System.out.print("Diagnóstico: ");
                    String diag = scanner.nextLine();
                    System.out.print("Tratamiento: ");
                    String trat = scanner.nextLine();
                    service.agregarConsulta(motivoCons, diag, trat, LocalDate.now());
                    System.out.println("Consulta registrada con éxito.");
                    break;
                case 6:
                    mostrarConsultas();
                    break;
                case 7:
                    mostrarConsultas();
                    if (service.listarConsultas().getTamano() > 0) {
                        System.out.print("Ingrese el número de la consulta a actualizar: ");
                        int indiceActualizarCons = Integer.parseInt(scanner.nextLine()) - 1;
                        System.out.print("Nuevo motivo: ");
                        String nuevoMotivoCons = scanner.nextLine();
                        System.out.print("Nuevo diagnóstico: ");
                        String nuevoDiag = scanner.nextLine();
                        System.out.print("Nuevo tratamiento: ");
                        String nuevoTrat = scanner.nextLine();
                        
                        if (service.actualizarConsulta(indiceActualizarCons, nuevoMotivoCons, nuevoDiag, nuevoTrat)) {
                            System.out.println("Consulta actualizada exitosamente.");
                        } else {
                            System.out.println("Error: No se encontró la consulta.");
                        }
                    }
                    break;
                case 8:
                    mostrarConsultas();
                    if (service.listarConsultas().getTamano() > 0) {
                        System.out.print("Ingrese el número de la consulta a eliminar: ");
                        int indiceEliminarCons = Integer.parseInt(scanner.nextLine()) - 1;
                        
                        if (service.eliminarConsulta(indiceEliminarCons)) {
                            System.out.println("Consulta eliminada exitosamente.");
                        } else {
                            System.out.println("Error: No se pudo eliminar la consulta.");
                        }
                    }
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
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

    private void mostrarConsultas() {
        System.out.println("\n--- Lista de Consultas ---");
        int tamanoConsultas = service.listarConsultas().getTamano();
        if (tamanoConsultas == 0) {
            System.out.println("No hay consultas registradas.");
        } else {
            for (int i = 0; i < tamanoConsultas; i++) {
                Consulta c = service.listarConsultas().obtener(i);
                System.out.println((i + 1) + ". Motivo: " + c.getMotivo() + " | Diagnóstico: " + c.getDiagnostico());
            }
        }
    }
}