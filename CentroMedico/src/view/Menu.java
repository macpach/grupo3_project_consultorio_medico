package view;

import java.time.LocalDate;
import utils.ConsoleUtils; 
import model.domain.Cita;
import model.domain.Consulta;
import model.domain.Medico;
import model.domain.Paciente;
import model.service.Consultorio;

public class Menu {
    private Consultorio service;
    private Medico medicoMock; 

    public Menu() {
        Paciente paciente = menuPaciente();     
        this.service = new Consultorio(paciente);
        this.medicoMock = menuMedico();
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
            System.out.println("0. Salir");
            
            opcion = ConsoleUtils.leerEntero("Seleccione un rol: "); 

            switch (opcion) {
                case 1:
                    submenuPaciente();
                    break;
                case 2:
                    submenuMedico();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
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
                case 1:
                    String hora = ConsoleUtils.leerTexto("Hora de la cita (Ej. 10:00 AM): ");
                    String motivo = ConsoleUtils.leerTexto("Motivo: ");
                    service.agregarCita(new Cita(LocalDate.now(), hora, motivo, null, medicoMock));
                    System.out.println("Cita agregada con éxito.");
                    break;
                case 2:
                    mostrarCitas();
                    break;
                case 3:
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
                    break;
                case 4:
                    mostrarCitas();
                    if (service.listarCitas().getTamano() > 0) {
                        int indiceEliminar = ConsoleUtils.leerEntero("Ingrese el número de la cita a eliminar: ") - 1;
                        
                        if (service.eliminarCita(indiceEliminar)) {
                            System.out.println("Cita eliminada exitosamente.");
                        } else {
                            System.out.println("Error: No se pudo eliminar la cita.");
                        }
                    }
                    break;
                case 0:
                    System.out.println("Regresando al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void submenuMedico() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- MENÚ MÉDICO (CONSULTAS) ---");
            System.out.println("1. Agregar Consulta");
            System.out.println("2. Listar Consultas");
            System.out.println("3. Actualizar Consulta");
            System.out.println("4. Eliminar Consulta");
            System.out.println("0. Volver al Menú Principal");
            
            opcion = ConsoleUtils.leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    String motivoCons = ConsoleUtils.leerTexto("Motivo de consulta: ");
                    String diag = ConsoleUtils.leerTexto("Diagnóstico: ");
                    String trat = ConsoleUtils.leerTexto("Tratamiento: ");
                    service.agregarConsulta(motivoCons, diag, trat, LocalDate.now());
                    System.out.println("Consulta registrada con éxito.");
                    break;
                case 2:
                    mostrarConsultas();
                    break;
                case 3:
                    mostrarConsultas();
                    if (service.listarConsultas().getTamano() > 0) {
                        int indiceActualizarCons = ConsoleUtils.leerEntero("Ingrese el número de la consulta a actualizar: ") - 1;
                        String nuevoMotivoCons = ConsoleUtils.leerTexto("Nuevo motivo: ");
                        String nuevoDiag = ConsoleUtils.leerTexto("Nuevo diagnóstico: ");
                        String nuevoTrat = ConsoleUtils.leerTexto("Nuevo tratamiento: ");
                        
                        if (service.actualizarConsulta(indiceActualizarCons, nuevoMotivoCons, nuevoDiag, nuevoTrat)) {
                            System.out.println("Consulta actualizada exitosamente.");
                        } else {
                            System.out.println("Error: No se encontró la consulta.");
                        }
                    }
                    break;
                case 4:
                    mostrarConsultas();
                    if (service.listarConsultas().getTamano() > 0) {
                        int indiceEliminarCons = ConsoleUtils.leerEntero("Ingrese el número de la consulta a eliminar: ") - 1;
                        
                        if (service.eliminarConsulta(indiceEliminarCons)) {
                            System.out.println("Consulta eliminada exitosamente.");
                        } else {
                            System.out.println("Error: No se pudo eliminar la consulta.");
                        }
                    }
                    break;
                case 0:
                    System.out.println("Regresando al menú principal...");
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