package model.domain;

import java.time.LocalDate;
public class cita {

    private LocalDate fecha;
    private String hora;
    private String motivo;
    private paciente paciente;
    private medico medico;

    public cita(LocalDate fecha, String hora, String motivo, paciente paciente, medico medico) {
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.paciente = paciente;
        this.medico = medico;
    }

    // Getters y Setters
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(paciente paciente) {
        this.paciente = paciente;
    }

    public medico getMedico() {
        return medico;
    }

    public void setMedico(medico medico) {
        this.medico = medico;
    }

}
