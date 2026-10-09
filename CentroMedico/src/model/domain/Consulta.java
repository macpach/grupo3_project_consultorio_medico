package model.domain;

import java.security.KeyStore.LoadStoreParameter;
import java.time.LocalDate;

public class Consulta {
    
    private String motivo;
    private String diagnostico;
    private String tratamiento;
    private LocalDate fecha;
    private Paciente paciente;

    public Consulta(String motivo, String diagnostico, String tratamiento, Paciente paciente) {
        this.motivo = motivo;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.fecha = LocalDate.now();
        this.paciente = paciente;
    }

    // Getters y Setters
    public String getMotivo() {
        return motivo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}