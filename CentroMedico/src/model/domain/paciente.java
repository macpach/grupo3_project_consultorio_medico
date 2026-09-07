package model.domain;
public class paciente extends persona {

    private int edad;
    private String eps;

    public paciente(String identificacion, String nombre, String telefono, int edad, String eps) {
        super(identificacion, nombre, telefono);
        this.edad = edad;
        this.eps = eps;
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

}
