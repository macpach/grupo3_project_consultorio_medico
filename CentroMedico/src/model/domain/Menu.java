package model.domain;

import java.time.LocalDate;
import java.util.Scanner;

public class Menu {
    private Consultorio service;
    private Scanner scanner;
    private Medico medicoMock; 

    public Menu(Consultorio service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
        this.medicoMock = new Medico("43210987", "Dra. María", "3109876543", "General", "RM-123");
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
                    System.out.println("--- Lista de Consultas ---");
                    int tamanoConsultas = service.listarConsultas().getTamano();
                    if (tamanoConsultas == 0) {
                        System.out.println("No hay consultas registradas.");
                    } else {
                        for (int i = 0; i < tamanoConsultas; i++) {
                            Consulta c = service.listarConsultas().obtener(i);
                            System.out.println((i + 1) + ". Motivo: " + c.getMotivo() + " | Diagnóstico: " + c.getDiagnostico());
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

    // Método de ayuda para no repetir el código de imprimir citas
    private void mostrarCitas() {
        System.out.println("--- Lista de Citas ---");
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
}