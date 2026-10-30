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
import model.domain.AgendaCitas;
import model.domain.Cita;
import model.domain.Medico;
import model.domain.Paciente;
import model.structures.NodoDoble;

public class AgendaService {

    private static final String ARCHIVO_AGENDA = "data/agenda.txt";
    private static final String SEPARADOR = ";";

    // Una sola agenda por Service
    private final AgendaCitas agenda = new AgendaCitas("Agenda del consultorio");
    private final PacienteService pacienteService;
    private final MedicoService medicoService;

    // pacienteService y medicoService deben crearse ANTES: sus datos ya estan
    // cargados cuando esta agenda se carga y enlaza cada cita por id
    public AgendaService(PacienteService pacienteService, MedicoService medicoService) {
        this.pacienteService = pacienteService;
        this.medicoService = medicoService;
        cargarAgenda(); // el texto que devuelve se ignora aqui
    }

    // ---------- Funcionalidad (Parte 4) ----------
    public void registrarCita(String idPaciente, String idMedico, LocalDate fecha, String hora, String motivo) {
        Paciente paciente = pacienteService.buscarPaciente(idPaciente);
        if (paciente == null) {
            throw new IllegalArgumentException("No existe un paciente con identificación " + idPaciente);
        }
        Medico medico = buscarMedico(idMedico);
        if (medico == null) {
            throw new IllegalArgumentException("No existe un médico con identificación " + idMedico);
        }
        agenda.getCitas().insertarAlFinal(new Cita(fecha, hora, motivo, paciente, medico));
    }

    public String listarCitasEnOrden() {
        if (agenda.getCitas().isEmpty()) {
            return "No hay citas en la agenda";
        }
        return agenda.getCitas().recorrerAdelante().trim();
    }

    public String listarCitasEnOrdenInverso() {
        if (agenda.getCitas().isEmpty()) {
            return "No hay citas en la agenda";
        }
        return agenda.getCitas().recorrerAtras().trim();
    }

    // El cursor vive en la lista; este metodo lo deja en la ultima cita
    // para poder hojear hacia atras
    public Cita irAUltimaCita() {
        return agenda.getCitas().irAlFinal();
    }

    public Cita verCitaAnterior() {
        return agenda.getCitas().anterior(); // NoSuchElementException si no hay anterior
    }

    public Cita verCitaSiguiente() {
        return agenda.getCitas().siguiente(); // NoSuchElementException si no hay siguiente
    }

    // Solo quita la cita de la agenda; el paciente y su lista de citas no se tocan
    public Cita cancelarCita() {
        return agenda.getCitas().retirarActual(); // NoSuchElementException sin seleccion
    }

    public int contarCitas() {
        return agenda.getCitas().size();
    }

    private Medico buscarMedico(String idMedico) {
        return medicoService.buscarMedico(idMedico);
    }

    // ---------- Persistencia (Parte 5) ----------
    private String limpiar(String texto) {
        return texto == null ? "" : texto.replace(SEPARADOR, ",").replace("\n", " ");
    }

    private void escribirAgenda() throws IOException {
        File carpeta = new File(ARCHIVO_AGENDA).getParentFile();
        if (carpeta != null) {
            carpeta.mkdirs();
        }

        // de head a tail (adelante)
        StringBuilder texto = new StringBuilder();
        NodoDoble<Cita> actual = agenda.getCitas().getHead();
        while (actual != null) {
            Cita c = actual.getDato();
            texto.append(String.join(SEPARADOR,
                    limpiar(c.getPaciente().getIdentificacion()),
                    limpiar(c.getMedico().getIdentificacion()),
                    String.valueOf(c.getFecha()),
                    limpiar(c.getHora()),
                    limpiar(c.getMotivo())))
                 .append("\n");
            actual = actual.getSiguiente();
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARCHIVO_AGENDA))) {
            escritor.write(texto.toString());
        }
    }

    public String guardarAgenda() {
        try {
            escribirAgenda();
            return "Se guardaron " + agenda.getCitas().size() + " citas en " + ARCHIVO_AGENDA;
        } catch (IOException e) {
            return "No se pudo guardar: " + e.getMessage();
        }
    }

    public String cargarAgenda() {
        File archivo = new File(ARCHIVO_AGENDA);

        // cargar reemplaza el contenido: evita duplicados al cargar dos veces
        while (agenda.getCitas().getHead() != null) {
            agenda.getCitas().eliminar(agenda.getCitas().getHead());
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(SEPARADOR, -1);
                if (campos.length != 5) {
                    continue; // linea con campos de menos o de mas
                }
                Paciente paciente = pacienteService.buscarPaciente(campos[0]);
                Medico medico = buscarMedico(campos[1]);
                if (paciente == null || medico == null) {
                    continue; // no se puede enlazar: se omite la linea
                }
                try {
                    LocalDate fecha = LocalDate.parse(campos[2]);
                    // insertarAlFinal: mismo orden del archivo
                    agenda.getCitas().insertarAlFinal(new Cita(fecha, campos[3], campos[4], paciente, medico));
                } catch (DateTimeException e) {
                    // fecha invalida: se omite la linea
                }
            }
        } catch (FileNotFoundException e) {
            return "No existe " + ARCHIVO_AGENDA + "; se inicia con la agenda vacía";
        } catch (IOException e) {
            return "No se pudo leer: " + e.getMessage();
        }

        return "Se cargaron " + agenda.getCitas().size() + " citas";
    }
}