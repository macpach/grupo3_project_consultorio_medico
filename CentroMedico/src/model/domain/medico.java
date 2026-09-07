package model.domain;

public class medico extends persona {
    
    private String especialidad;
    private String numeroRegistro;

    public medico(String identificacion, String nombre, String telefono, String especialidad, String numeroRegistro) {
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
