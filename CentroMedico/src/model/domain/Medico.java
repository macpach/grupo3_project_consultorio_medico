package model.domain;

public class Medico extends Persona {
    
    private String especialidad;
    private String numeroRegistro;

    public Medico(String identificacion, String nombre, String telefono, String especialidad, String numeroRegistro) {
        super(identificacion, nombre, telefono);
        this.especialidad = especialidad;
        this.numeroRegistro = numeroRegistro;
    }

    @Override
    public String rolEnConsulta() {
        return "Rol: Médico | Especialidad: " + especialidad + " | Registro: " + numeroRegistro;
    }

    // Getters y Setters
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }
}
