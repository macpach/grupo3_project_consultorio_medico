package service;

import java.time.LocalDate;
import model.domain.Cita;
import model.domain.Medico;
import model.domain.Paciente;
import model.domain.SalaDeEspera; 
import model.structures.ListaSimple;

public class Consultorio {
    private final Paciente pacienteActual; 
    private SalaDeEspera salaDeEspera; 

    public Consultorio(Paciente paciente) {
        this.pacienteActual = paciente;
        this.salaDeEspera = new SalaDeEspera("Sala de Espera Principal");
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

    // --- Operaciones para la Sala de Espera (TAD Cola) ---
    public void registrarLlegadaPaciente(Paciente paciente) {
        salaDeEspera.getFila().encolar(paciente);
    }

    public Paciente atenderSiguiente() {
        if (sinClientesEnEspera()) {
            return null;
        }
        return salaDeEspera.getFila().desencolar();
    }

    public Paciente consultarSiguiente() {
        if (sinClientesEnEspera()) {
            return null;
        }
        return salaDeEspera.getFila().frente();
    }

    public boolean sinClientesEnEspera() {
        return salaDeEspera.getFila().estaVacia();
    }

    public int contarEnEspera() {
        return salaDeEspera.getFila().tamanio();
    }

    public String listarEnEspera() {
        if (sinClientesEnEspera()) {
            return "No hay clientes en espera";
        }

        StringBuilder sb = new StringBuilder();
        int total = contarEnEspera();

        for (int i = 0; i < total; i++) {
            Paciente p = salaDeEspera.getFila().desencolar();
            sb.append(i + 1).append(". ").append(p.getNombre())
              .append(" (ID: ").append(p.getIdentificacion()).append(")\n");
            
            salaDeEspera.getFila().encolar(p);
        }

        return sb.toString().trim();
    }
}