package model.domain;

import java.time.LocalDate;

public class Consultorio {
    private Paciente pacienteActual;

    public Consultorio(Paciente paciente) {
        this.pacienteActual = paciente;
    }

    // --- Operaciones para Citas (CRUD Completo) ---
    
    public void agregarCita(Cita cita) {
        pacienteActual.getCitas().insertarFinal(cita);
    }

    public ListaSimple<Cita> listarCitas() {
        return pacienteActual.getCitas();
    }

    public boolean actualizarCita(int indice, String nuevaHora, String nuevoMotivo, Medico medico) {
        Cita citaVieja = pacienteActual.getCitas().obtener(indice);
        if (citaVieja != null) {
            Cita citaNueva = new Cita(LocalDate.now(), nuevaHora, nuevoMotivo, pacienteActual, medico);
            return pacienteActual.getCitas().actualizar(citaVieja, citaNueva);
        }
        return false;
    }

    public boolean eliminarCita(int indice) {
        Cita citaAEliminar = pacienteActual.getCitas().obtener(indice);
        if (citaAEliminar != null) {
            return pacienteActual.getCitas().eliminarPorValor(citaAEliminar);
        }
        return false;
    }

    // --- Operaciones para Consultas ---
    public void agregarConsulta(String motivo, String diagnostico, String tratamiento, LocalDate fecha) {
        Consulta nueva = new Consulta(motivo, diagnostico, tratamiento, fecha);
        pacienteActual.getHistorialConsultas().insertarFinal(nueva);
    }

    public ListaSimple<Consulta> listarConsultas() {
        return pacienteActual.getHistorialConsultas();
    }
}
