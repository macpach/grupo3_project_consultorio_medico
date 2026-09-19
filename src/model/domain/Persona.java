package model.domain;
public abstract class Persona implements RolClinico {

    private String identificacion;
    private String nombre;
    private String telefono;

    public Persona(String identificacion, String nombre, String telefono) {
        if (identificacion == null || identificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación no puede ser nula ni estar vacía.");
        }
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    @Override
    public String datosResumen() {
        return "ID: " + identificacion + " | Nombre: " + nombre + " | Teléfono: " + telefono;
    }

    @Override
    public abstract String rolEnConsulta();

    // Getters y Setters
    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación no puede ser nula ni estar vacía.");
        }
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
}
