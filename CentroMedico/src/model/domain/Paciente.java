package model.domain;
import model.structures.Pila;
import java.time.LocalDate;
import model.structures.ListaSimple;

public class Paciente extends Persona {

    private int edad;
    private String eps;
    private Pila<Consulta> historialConsultas;
    private ListaSimple<Cita> citas;

    public Paciente(String identificacion, String nombre, String telefono, int edad, String eps) {
        super(identificacion, nombre, telefono);
        this.edad = edad;
        this.eps = eps;
        this.historialConsultas = new Pila<Consulta>();
        this.citas = new ListaSimple<>();
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
        Consulta historialConsultas = new Consulta(motivo, diagnostico, tratamiento, fecha);
        this.historialConsultas.push(historialConsultas);
    }

    public Pila<Consulta> getHistorialConsultas() {
        return historialConsultas;
    }

    public void registrarCita(Cita cita) {
        citas.insertarFinal(cita);
    }

    public ListaSimple<Cita> getCitas() {
        return citas;
    }
}