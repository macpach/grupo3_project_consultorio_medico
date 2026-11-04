package model.domain;
import model.structures.Pila;
import java.time.LocalDate;
import model.structures.ListaSimple;
import java.time.Period;

public class Paciente extends Persona {

    private int edad;
    private String eps;
    private Pila<Consulta> historialConsultas;
    private ListaSimple<Cita> citas;
    private LocalDate fechaNacimiento;
    private String tipoSangre;


    public Paciente(String identificacion, String nombre, String telefono,
                LocalDate fechaNacimiento, String tipoSangre) {
    this(identificacion, nombre, telefono, calcularEdad(fechaNacimiento), "No registrada");
    this.fechaNacimiento = fechaNacimiento;
    this.tipoSangre = tipoSangre;
   }

    public Paciente(String identificacion, String nombre, String telefono,
                int edad, String eps) {
    super(identificacion, nombre, telefono);
    this.edad = edad;
    this.eps = eps;
    this.historialConsultas = new Pila<>();
    this.citas = new ListaSimple<>();
    this.fechaNacimiento = null;
    this.tipoSangre = "No registrada";
   }

    private static int calcularEdad(LocalDate fecha) {
    return fecha == null ? 0 : Period.between(fecha, LocalDate.now()).getYears();
    }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
     public String getTipoSangre() { return tipoSangre; }

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
        Consulta historialConsultas = new Consulta(motivo, diagnostico, tratamiento, fecha,this); 
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
