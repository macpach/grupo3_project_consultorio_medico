package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import model.domain.Medico;
import model.structures.ListaSimple;
import model.structures.Nodo;

public class MedicoService {

    private static final String ARCHIVO_MEDICOS = "data/medicos.txt";
    private static final String SEPARADOR = ";";

    private final ListaSimple<Medico> medicos = new ListaSimple<>();

    public MedicoService() {
        cargarMedicos(); // el texto que devuelve se ignora aqui
    }

    // ---------- Funcionalidad ----------
    public boolean registrarMedico(String identificacion, String nombre, String telefono,
                                   String especialidad, String numeroRegistro) {
        if (buscarMedico(identificacion) != null) {
            return false; // ya existe
        }
        medicos.insertarFinal(new Medico(identificacion, nombre, telefono, especialidad, numeroRegistro));
        return true;
    }

    public Medico buscarMedico(String identificacion) {
        Nodo<Medico> actual = medicos.getCabeza();
        while (actual != null) {
            if (actual.getDato().getIdentificacion().equals(identificacion)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public int contarMedicos() {
        return medicos.getTamano();
    }

    // ---------- Persistencia ----------
    private String limpiar(String texto) {
        return texto == null ? "" : texto.replace(SEPARADOR, ",").replace("\n", " ");
    }

    private void escribirMedicos() throws IOException {
        File carpeta = new File(ARCHIVO_MEDICOS).getParentFile();
        if (carpeta != null) {
            carpeta.mkdirs();
        }

        StringBuilder texto = new StringBuilder();
        Nodo<Medico> actual = medicos.getCabeza();
        while (actual != null) {
            Medico m = actual.getDato();
            texto.append(String.join(SEPARADOR,
                    limpiar(m.getIdentificacion()),
                    limpiar(m.getNombre()),
                    limpiar(m.getTelefono()),
                    limpiar(m.getEspecialidad()),
                    limpiar(m.getNumeroRegistro())))
                 .append("\n");
            actual = actual.getSiguiente();
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARCHIVO_MEDICOS))) {
            escritor.write(texto.toString());
        }
    }

    public String guardarMedicos() {
        try {
            escribirMedicos();
            return "Se guardaron " + medicos.getTamano() + " médicos en " + ARCHIVO_MEDICOS;
        } catch (IOException e) {
            return "No se pudo guardar: " + e.getMessage();
        }
    }

    public String cargarMedicos() {
        File archivo = new File(ARCHIVO_MEDICOS);

        medicos.vaciar();

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(SEPARADOR, -1);
                if (campos.length != 5) {
                    continue;
                }
                try {
                    Medico m = new Medico(campos[0], campos[1], campos[2], campos[3], campos[4]);
                    if (buscarMedico(m.getIdentificacion()) == null) {
                        medicos.insertarFinal(m);
                    }
                } catch (IllegalArgumentException e) {
                    // identificacion vacia: se omite la linea
                }
            }
        } catch (FileNotFoundException e) {
            return "No existe " + ARCHIVO_MEDICOS + "; se inicia con la lista vacía";
        } catch (IOException e) {
            return "No se pudo leer: " + e.getMessage();
        }

        return "Se cargaron " + medicos.getTamano() + " médicos";
    }
}