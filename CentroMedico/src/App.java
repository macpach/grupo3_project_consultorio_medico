import model.domain.*;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
    
    paciente paciente = new paciente("1017123456", "Juan Pérez", "3001234567", 20, "Sura");
        medico medico = new medico("43210987", "Dra. María", "3109876543", "General", "RM-123");

       
        System.out.println(paciente.datosResumen());
        System.out.println(paciente.rolEnConsulta());

        System.out.println(medico.datosResumen());
        System.out.println(medico.rolEnConsulta());

       
        cita cita = new cita(LocalDate.now(), "10:00 AM", "Chequeo", paciente, medico);
        System.out.println("Cita asignada a " + cita.getPaciente().getNombre() + " con " + cita.getMedico().getNombre());

       
        try {
            
            new paciente("", "Pedro", "0000", 25, "Sura");
        } catch (IllegalArgumentException e) {
            System.out.println("Excepción capturada: " + e.getMessage());
        }

}
}
