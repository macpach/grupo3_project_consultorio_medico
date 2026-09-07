package model.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Paciente extends Persona {

    private int edad;
    private String eps;
    private List<Consulta> historialConsultas;

    public Paciente(String identificacion, String nombre, String telefono, int edad, String eps) {
        super(identificacion, nombre, telefono);
        this.edad = edad;
        this.eps = eps;
        this.historialConsultas = new ArrayList<>();
    }

    @Override
    public String rolEnConsulta() {
        return "Rol: Paciente | EPS: " + eps + " | Edad: " + edad + " años";
    }

    // Getters y Setters
    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    public void registrarConsulta(String motivo, String diagnostico, String tratamiento, LocalDate fecha) {
        historialConsultas.add(new Consulta(motivo, diagnostico, tratamiento, fecha));
    }

    public List<Consulta> getHistorialConsultas() {
        return historialConsultas;
    }

}
