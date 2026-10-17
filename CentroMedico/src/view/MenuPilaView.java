package view;

import java.time.LocalDate;
import model.domain.Consulta;
import service.ConsultorioPila;
import utils.ConsoleUtils;

public class MenuPilaView {
    private final ConsultorioPila consultorio;

    public MenuPilaView(ConsultorioPila consultorio) {
        this.consultorio = consultorio;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = ConsoleUtils.leerEntero("Elija una opción: ");
            switch (opcion) {
                case 1 -> registrar();
                case 2 -> retirar();
                case 3 -> consultarUltima();
                case 4 -> verSiHayConsultas();
                case 5 -> contar();
                case 6 -> listar();
                case 0 -> System.out.println("Saliendo del menú de pilas...");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    private void mostrarOpciones() {
        System.out.println("\n--- MENÚ DE CONSULTAS (PILA) ---");
        System.out.println("1. Registrar consulta (push)");
        System.out.println("2. Retirar última consulta (pop)");
        System.out.println("3. Ver última consulta (peek)");
        System.out.println("4. ¿Hay consultas por retirar? (isEmpty)");
        System.out.println("5. Cantidad de consultas (size)");
        System.out.println("6. Listar consultas (Parte 7)");
        System.out.println("0. Salir");
    }

    private void registrar() {
        String motivo = ConsoleUtils.leerTexto("Motivo de consulta: ");
        String diagnostico = ConsoleUtils.leerTexto("Diagnóstico: ");
        String tratamiento = ConsoleUtils.leerTexto("Tratamiento: ");
        consultorio.registrarConsulta(motivo, diagnostico, tratamiento, LocalDate.now());
        System.out.println("Consulta registrada con éxito.");
    }

    private void retirar() {
        if (consultorio.sinConsultasPorRetirar()) {
            System.out.println("No hay consultas por retirar");
            return;
        }
        Consulta retirada = consultorio.retirarUltimaConsulta();
        System.out.println("Consulta retirada:");
        imprimirConsulta(retirada);
    }

    private void consultarUltima() {
        if (consultorio.sinConsultasPorRetirar()) {
            System.out.println("No hay consultas por retirar");
            return;
        }
        System.out.println("Última consulta registrada:");
        imprimirConsulta(consultorio.consultarUltimaConsulta());
    }

    private void verSiHayConsultas() {
        if (consultorio.sinConsultasPorRetirar()) {
            System.out.println("No hay consultas por retirar");
        } else {
            System.out.println("Hay consultas registradas");
        }
    }

    private void contar() {
        System.out.println("Consultas registradas: " + consultorio.contarConsultas());
    }

    private void listar() {
    System.out.println("\n--- Consultas (de la más reciente a la más antigua) ---");
    System.out.print(consultorio.listarConsultas());
}

    private void imprimirConsulta(Consulta c) {
        System.out.println("Motivo: " + c.getMotivo()
                + " | Diagnóstico: " + c.getDiagnostico()
                + " | Tratamiento: " + c.getTratamiento());
    }
}