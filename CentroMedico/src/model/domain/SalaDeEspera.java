package model.domain;

import model.structures.Cola;

public class SalaDeEspera {
    private String nombre;
    private Cola<Paciente> fila;

    public SalaDeEspera(String nombre) {
        this.nombre = nombre;
        this.fila = new Cola<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Cola<Paciente> getFila() {
        return fila;
    }
}