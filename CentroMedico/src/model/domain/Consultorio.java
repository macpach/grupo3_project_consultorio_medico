package model.domain;

import java.time.LocalDate;

public class Consultorio {
    private Paciente pacienteActual;

    public Consultorio(Paciente paciente) {
        this.pacienteActual = paciente;
    }

    // --- Operaciones para Citas ---
    public void agregarCita(Cita cita) {
        pacienteActual.getCitas().insertarFinal(cita);
    }

    public ListaSimple<Cita> listarCitas() {
        return pacienteActual.getCitas();
    }

    public boolean eliminarCita(Cita cita) {
        return pacienteActual.getCitas().eliminarPorValor(cita);
    }

    // --- Operaciones para Consultas ---
    public void agregarConsulta(String motivo, String diagnostico, String tratamiento, LocalDate fecha) {
        Consulta nueva = new Consulta(motivo, diagnostico, tratamiento, fecha);
        pacienteActual.getHistorialConsultas().insertarFinal(nueva);
    }

    public ListaSimple<Consulta> listarConsultas() {
        return pacienteActual.getHistorialConsultas();
    }

    public boolean eliminarConsulta(Consulta consulta) {
        return pacienteActual.getHistorialConsultas().eliminarPorValor(consulta);
    }
}
