package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import model.domain.Paciente;
import model.structures.ListaSimple;
import model.structures.Nodo;

public class PacienteService {

    private static final String ARCHIVO_PACIENTES = "data/pacientes.txt";
    private static final String SEPARADOR = ";";

    // Un solo listado por Service
    private final ListaSimple<Paciente> pacientes = new ListaSimple<>();

    public PacienteService() {
        cargarPacientes(); // el texto que devuelve se ignora aqui
    }

    // ---------- Funcionalidad ----------
    public boolean registrarPaciente(String identificacion, String nombre, String telefono,
                                     LocalDate fechaNacimiento, String tipoSangre) {
        if (buscarPaciente(identificacion) != null) {
            return false; // ya existe
        }
        pacientes.insertarFinal(new Paciente(identificacion, nombre, telefono, fechaNacimiento, tipoSangre));
        return true;
    }

    public Paciente buscarPaciente(String identificacion) {
        Nodo<Paciente> actual = pacientes.getCabeza();
        while (actual != null) {
            if (actual.getDato().getIdentificacion().equals(identificacion)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public int contarPacientes() {
        return pacientes.getTamano();
    }

    // ---------- Persistencia ----------
    private String limpiar(String texto) {
        // un ';' dentro del texto romperia la linea
        return texto == null ? "" : texto.replace(SEPARADOR, ",").replace("\n", " ");
    }

    private void escribirPacientes() throws IOException {
        File carpeta = new File(ARCHIVO_PACIENTES).getParentFile();
        if (carpeta != null) {
            carpeta.mkdirs();
        }

        // del primero al ultimo (adelante), para que el orden se conserve al cargar
        StringBuilder texto = new StringBuilder();
        Nodo<Paciente> actual = pacientes.getCabeza();
        while (actual != null) {
            Paciente p = actual.getDato();
            String fecha = p.getFechaNacimiento() == null ? "" : p.getFechaNacimiento().toString();
            texto.append(String.join(SEPARADOR,
                    limpiar(p.getIdentificacion()),
                    limpiar(p.getNombre()),
                    limpiar(p.getTelefono()),
                    fecha,
                    limpiar(p.getTipoSangre())))
                 .append("\n");
            actual = actual.getSiguiente();
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARCHIVO_PACIENTES))) {
            escritor.write(texto.toString());
        }
    }

    public String guardarPacientes() {
        try {
            escribirPacientes();
            return "Se guardaron " + pacientes.getTamano() + " pacientes en " + ARCHIVO_PACIENTES;
        } catch (IOException e) {
            return "No se pudo guardar: " + e.getMessage();
        }
    }

    public String cargarPacientes() {
        File archivo = new File(ARCHIVO_PACIENTES);

        // cargar reemplaza el contenido: evita duplicados al cargar dos veces
        pacientes.vaciar();

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(SEPARADOR, -1);
                if (campos.length != 5) {
                    continue; // linea con campos de menos o de mas
                }
                try {
                    LocalDate fecha = campos[3].isEmpty() ? null : LocalDate.parse(campos[3]);
                    Paciente p = new Paciente(campos[0], campos[1], campos[2], fecha, campos[4]);
                    if (buscarPaciente(p.getIdentificacion()) == null) {
                        pacientes.insertarFinal(p); // mismo orden del archivo
                    }
                } catch (DateTimeException | IllegalArgumentException e) {
                    // fecha invalida o identificacion vacia: se omite la linea
                }
            }
        } catch (FileNotFoundException e) {
            return "No existe " + ARCHIVO_PACIENTES + "; se inicia con la lista vacía";
        } catch (IOException e) {
            return "No se pudo leer: " + e.getMessage();
        }

        return "Se cargaron " + pacientes.getTamano() + " pacientes";
    }
}
