package model.domain;

import model.structures.ListaDoble;

public class Agenda {
    private String fecha;
    private ListaDoble<Cita> citas;

    public Agenda(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha no puede ser nula ni vacia");
        }
        this.fecha = fecha;
        this.citas = new ListaDoble<>();
    }

    public String getFecha() {
        return fecha;
    }

    public ListaDoble<Cita> getCitas() {
        return citas;
    }
}