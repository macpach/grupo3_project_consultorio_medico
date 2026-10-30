package model.domain;
 
import model.structures.ListaDoble;
 
public class AgendaCitas {
 
    private final String nombre;
    private final ListaDoble<Cita> citas;
 
    public AgendaCitas(String nombre) {
        this.nombre = nombre;
        this.citas = new ListaDoble<>();
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public ListaDoble<Cita> getCitas() {
        return citas;
    }
}