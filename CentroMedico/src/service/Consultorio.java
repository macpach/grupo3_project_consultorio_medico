package service;

import java.time.LocalDate;
import model.domain.Cita;
import model.domain.Consulta;
import model.domain.Medico;
import model.domain.Paciente;
import model.structures.ListaSimple;

public class Consultorio {
    private Paciente pacienteActual;

    public Consultorio(Paciente paciente) {
        this.pacienteActual = paciente;
    }
    
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

    public void agregarConsulta(String motivo, String diagnostico, String tratamiento, LocalDate fecha) {
        Consulta nueva = new Consulta(motivo, diagnostico, tratamiento,pacienteActual);
        pacienteActual.getHistorialConsultas().insertarFinal(nueva);
    }

    public ListaSimple<Consulta> listarConsultas() {
        return pacienteActual.getHistorialConsultas();
    }
    public boolean actualizarConsulta(int indice, String nuevoMotivo, String nuevoDiagnostico, String nuevoTratamiento) {
        Consulta consultaVieja = pacienteActual.getHistorialConsultas().obtener(indice);
        if (consultaVieja != null) {
            Consulta consultaNueva = new Consulta(nuevoMotivo, nuevoDiagnostico, nuevoTratamiento, pacienteActual);
            return pacienteActual.getHistorialConsultas().actualizar(consultaVieja, consultaNueva);
        }
        return false;
    }

    public boolean eliminarConsulta(int indice) {
        Consulta consultaAEliminar = pacienteActual.getHistorialConsultas().obtener(indice);
        if (consultaAEliminar != null) {
            return pacienteActual.getHistorialConsultas().eliminarPorValor(consultaAEliminar);
        }
        return false;
    }
    
    public Consulta buscarFecha(LocalDate Fecha){

        ListaSimple<Consulta> historial = pacienteActual.getHistorialConsultas();
        
        
        for (int i = 0; i < historial.getTamano(); i++) {
            Consulta consultaActual = historial.obtener(i);
            
        
            if (consultaActual != null && consultaActual.getFecha().equals(Fecha)) {
                return consultaActual; 
            }
        }
        return null;
    }
}
