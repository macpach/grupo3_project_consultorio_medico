package service;

import java.time.LocalDate;
import model.domain.Consulta;
import model.domain.Paciente;
import model.structures.Pila;

public class ConsultorioPila {
    private final Paciente pacienteActual; 

    public ConsultorioPila(Paciente paciente) {
        this.pacienteActual = paciente;
    }

    // --- Operaciones para Consultas (una por operación del TAD Pila) ---

    // push → no devuelve nada
    public void registrarConsulta(String motivo, String diagnostico, String tratamiento, LocalDate fecha) {
    if (motivo == null || motivo.trim().isEmpty()
            || diagnostico == null || diagnostico.trim().isEmpty()
            || tratamiento == null || tratamiento.trim().isEmpty()
            || fecha == null) {
        throw new IllegalArgumentException("Motivo, diagnóstico y tratamiento no pueden estar vacíos");
    }

    Consulta nueva = new Consulta(motivo, diagnostico, tratamiento, fecha, pacienteActual);
    pacienteActual.getHistorialConsultas().push(nueva);
}
    // pop → devuelve la consulta retirada
    public Consulta retirarUltimaConsulta() {
        return pacienteActual.getHistorialConsultas().pop();
    }

    // peek → devuelve la última consulta, sin retirarla
    public Consulta consultarUltimaConsulta() {
        return pacienteActual.getHistorialConsultas().peek();
    }

    // isEmpty → boolean
    public boolean sinConsultasPorRetirar() {
        return pacienteActual.getHistorialConsultas().isEmpty();
    }

    // size → int
    public int contarConsultas() {
        return pacienteActual.getHistorialConsultas().size();
    }

    // sexto método (Parte 7): lista sin destruir la pila
    public String listarConsultas() {
        Pila<Consulta> original = pacienteActual.getHistorialConsultas();

        if (original.isEmpty()) {
            return "No hay consultas por retirar";
        }

        Pila<Consulta> auxiliar = new Pila<>();
        StringBuilder texto = new StringBuilder();
        int contador = 1;

        while (!original.isEmpty()) {
            Consulta c = original.pop();
            texto.append(contador).append(". Motivo: ").append(c.getMotivo())
                 .append(" | Diagnóstico: ").append(c.getDiagnostico())
                 .append(" | Tratamiento: ").append(c.getTratamiento())
                 .append("\n");
            auxiliar.push(c);
            contador++;
        }

        while (!auxiliar.isEmpty()) {
            original.push(auxiliar.pop());
        }

        return texto.toString();
    }
}
