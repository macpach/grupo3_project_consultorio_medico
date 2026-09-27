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
            System.out.println("3. Agregar Consulta");
            System.out.println("4. Listar Consultas");
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
                    break;
                case 3:
                    System.out.print("Motivo de consulta: ");
                    String motivoCons = scanner.nextLine();
                    System.out.print("Diagnóstico: ");
                    String diag = scanner.nextLine();
                    System.out.print("Tratamiento: ");
                    String trat = scanner.nextLine();
                    service.agregarConsulta(motivoCons, diag, trat, LocalDate.now());
                    System.out.println("Consulta registrada con éxito.");
                    break;
                case 4:
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
}
