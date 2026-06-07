package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.*;
import com.jorgestor.backend.model.*;
import com.jorgestor.backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExamenService {

    private final AsignaturaService asignaturaService;
    private final PreguntaService preguntaService;
    private final ExamenRepository examenRepository;
    private final ExamenBorradorRepository examenBorradorRepository;
    private final AlumnoRepository alumnoRepository;
    private final ExamenBorradorPreguntaRepository examenBorradorPreguntaRepository;
    private final ExamenPreguntaRepository examenPreguntaRepository;
    private final PreguntaRepository preguntaRepository;
    private final ExamenRespuestaRepository examenRespuestaRepository;

    public ExamenService(AsignaturaService asignaturaService, PreguntaService preguntaService, 
                         ExamenRepository examenRepository, ExamenBorradorRepository examenBorradorRepository, 
                         AlumnoRepository alumnoRepository, ExamenBorradorPreguntaRepository examenBorradorPreguntaRepository,
                         ExamenPreguntaRepository examenPreguntaRepository, PreguntaRepository preguntaRepository,
                         ExamenRespuestaRepository examenRespuestaRepository) {
        this.asignaturaService = asignaturaService;
        this.preguntaService = preguntaService;
        this.examenRepository = examenRepository;
        this.examenBorradorRepository = examenBorradorRepository;
        this.alumnoRepository = alumnoRepository;
        this.examenBorradorPreguntaRepository = examenBorradorPreguntaRepository;
        this.examenPreguntaRepository = examenPreguntaRepository;
        this.preguntaRepository = preguntaRepository;
        this.examenRespuestaRepository = examenRespuestaRepository;
    }

    public GeneracionExitoDTO generarExamenes(GenerarExamenesDTO dto, Long docenteId) {
        examenBorradorRepository.deleteAll();

        Asignatura asignatura = asignaturaService.findEntityById(dto.getAsignaturaId());
        if (asignatura.getProfesor() != null && !asignatura.getProfesor().getId().equals(docenteId)) {
            throw new RuntimeException("No tiene permisos sobre esta asignatura");
        }

        List<String> temas = dto.getTemas();
        List<PreguntaDTO> banco = preguntaService.obtenerBancoPreguntas(asignatura.getId(), temas);
        Map<DificultadPregunta, List<PreguntaDTO>> bancoPorDificultad = banco.stream()
                .collect(Collectors.groupingBy(PreguntaDTO::getDificultad));

        Map<Long, Integer> resumen = new HashMap<>();

        for (ConfigGradoDTO config : dto.getConfiguracionesGrado()) {
            int creados = 0;
            Grado grado = asignatura.getGrados().stream()
                .filter(g -> g.getId().equals(config.getGradoId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("El grado no pertenece a esta asignatura"));

            for (int i = 0; i < config.getNumExamenes(); i++) {
                List<PreguntaDTO> seleccionadas = seleccionarPreguntas(config, config.getNumPreguntas(), bancoPorDificultad);
                
                ExamenBorrador borrador = new ExamenBorrador(asignatura, grado, dto.getTipoExamen(), generarClaveAleatoria());
                examenBorradorRepository.save(borrador);
                for (PreguntaDTO pDTO : seleccionadas) {
                    Pregunta p = preguntaRepository.findById(pDTO.getId()).orElseThrow();
                    examenBorradorPreguntaRepository.save(new ExamenBorradorPregunta(borrador, p));
                }
                creados++;
            }
            resumen.put(config.getGradoId(), creados);
        }

        return new GeneracionExitoDTO(resumen.values().stream().mapToInt(Integer::intValue).sum(), resumen);
    }

    private List<PreguntaDTO> seleccionarPreguntas(ConfigGradoDTO config, Integer totalPreguntas, Map<DificultadPregunta, List<PreguntaDTO>> banco) {
        List<PreguntaDTO> seleccion = new ArrayList<>();
        int facil = config.getProporcionFacil() != null ? config.getProporcionFacil() : 0;
        int media = config.getProporcionMedia() != null ? config.getProporcionMedia() : 0;
        int dificil = config.getProporcionDificil() != null ? config.getProporcionDificil() : 0;
        
        int suma = facil + media + dificil;
        if (suma == 0) { facil = 33; media = 33; dificil = 34; }
        else { facil = (facil * 100) / suma; media = (media * 100) / suma; dificil = 100 - facil - media; }

        int numFacil = (int) Math.round(totalPreguntas * (facil / 100.0));
        int numMedia = (int) Math.round(totalPreguntas * (media / 100.0));
        int numDificil = totalPreguntas - numFacil - numMedia;

        validarStock(banco.getOrDefault(DificultadPregunta.FACIL, new ArrayList<>()), numFacil, "Fácil");
        validarStock(banco.getOrDefault(DificultadPregunta.MEDIO, new ArrayList<>()), numMedia, "Media");
        validarStock(banco.getOrDefault(DificultadPregunta.DIFICIL, new ArrayList<>()), numDificil, "Difícil");

        seleccion.addAll(obtenerDisponibles(banco.getOrDefault(DificultadPregunta.FACIL, new ArrayList<>()), numFacil));
        seleccion.addAll(obtenerDisponibles(banco.getOrDefault(DificultadPregunta.MEDIO, new ArrayList<>()), numMedia));
        seleccion.addAll(obtenerDisponibles(banco.getOrDefault(DificultadPregunta.DIFICIL, new ArrayList<>()), numDificil));
        Collections.shuffle(seleccion);
        return seleccion;
    }

    private void validarStock(List<PreguntaDTO> banco, int solicitado, String dificultad) {
        if (banco.size() < solicitado) {
            throw new RuntimeException("No hay suficientes preguntas de dificultad " + dificultad + ". Solicitadas: " + solicitado + ", Disponibles: " + banco.size());
        }
    }

    private List<PreguntaDTO> obtenerDisponibles(List<PreguntaDTO> lista, int cantidad) {
        if (cantidad <= 0) return new ArrayList<>();
        List<PreguntaDTO> copia = new ArrayList<>(lista);
        Collections.shuffle(copia);
        return copia.subList(0, Math.min(copia.size(), cantidad));
    }

    public void persistirAsignaciones(List<Long> alumnoIds) {
        List<ExamenBorrador> borradores = examenBorradorRepository.findAll();
        
        if (borradores.isEmpty()) {
            throw new RuntimeException("No hay borradores de examen disponibles.");
        }

        Map<Long, List<ExamenBorrador>> borradoresPorGrado = borradores.stream()
                .collect(Collectors.groupingBy(b -> b.getGrado().getId()));

        for (Long alumnoId : alumnoIds) {
            Alumno alumno = alumnoRepository.findById(alumnoId)
                    .orElseThrow(() -> new RuntimeException("Alumno no encontrado: " + alumnoId));
            Long gradoId = alumno.getGrado().getId();
            
            List<ExamenBorrador> borradoresGrado = borradoresPorGrado.get(gradoId);
            if (borradoresGrado == null || borradoresGrado.isEmpty()) {
                throw new RuntimeException("No hay exámenes disponibles para el grado del alumno: " + alumno.getGrado().getTitulo() + " (ID: " + gradoId + ")");
            }
            
            ExamenBorrador borrador = borradoresGrado.remove(0);
            
            Examen examen = new Examen(alumno, borrador.getAsignatura(), borrador.getTipoExamen(), borrador.getClave(), EstadoExamen.ASIGNADO);
            examenRepository.save(examen);
            
            List<ExamenBorradorPregunta> preguntasBorrador = examenBorradorPreguntaRepository.findByExamenBorradorId(borrador.getId());
            for (ExamenBorradorPregunta ebp : preguntasBorrador) {
                examenPreguntaRepository.save(new ExamenPregunta(examen, ebp.getPregunta()));
                examenBorradorPreguntaRepository.delete(ebp);
            }
            
            examenBorradorRepository.delete(borrador);
        }
    }

    public void corregirTodosExamenes(Long docenteId) {
        List<Examen> examenesPendientes = obtenerExamenesParaCorregir(docenteId);
        for (Examen examen : examenesPendientes) {
            corregirExamen(examen.getId(), docenteId);
        }
    }

    public List<Examen> obtenerExamenesParaCorregir(Long docenteId) {
        return examenRepository.findAll().stream()
                .filter(e -> e.getAsignatura().getProfesor() != null && e.getAsignatura().getProfesor().getId().equals(docenteId))
                .filter(e -> e.getEstado() == EstadoExamen.ASIGNADO)
                .collect(Collectors.toList());
    }

    public List<Examen> obtenerTodosExamenesDocente(Long docenteId) {
        return examenRepository.findAll().stream()
                .filter(e -> e.getAsignatura().getProfesor() != null && e.getAsignatura().getProfesor().getId().equals(docenteId))
                .collect(Collectors.toList());
    }

    public DetalleExamenDTO obtenerDetalleExamen(Long examenId, Long docenteId) {
        Examen examen = examenRepository.findById(examenId)
                .orElseThrow(() -> new RuntimeException("Examen no encontrado"));

        if (examen.getAsignatura().getProfesor() == null || !examen.getAsignatura().getProfesor().getId().equals(docenteId)) {
            throw new RuntimeException("No tiene permisos para ver este examen");
        }

        List<ExamenRespuesta> respuestas = examenRespuestaRepository.findByExamenId(examenId);
        
        List<DetalleExamenDTO.PreguntaDetalleDTO> preguntasDetalle = respuestas.stream().map(er -> {
            Pregunta p = er.getPregunta();
            String respuestaCorrecta = p.getRespuestas().stream()
                    .filter(Respuesta::isEsCorrecta)
                    .map(Respuesta::getOpcion)
                    .findFirst().orElse("N/A");
            
            return new DetalleExamenDTO.PreguntaDetalleDTO(
                p.getEnunciado(),
                er.getRespuesta().getOpcion(),
                er.getRespuesta().isEsCorrecta(),
                respuestaCorrecta
            );
        }).collect(Collectors.toList());

        return new DetalleExamenDTO(
            examen.getId(),
            examen.getAlumno().getNombre() + " " + examen.getAlumno().getApellidos(),
            examen.getNotaFinal(),
            preguntasDetalle
        );
    }

    public Examen corregirExamen(Long examenId, Long docenteId) {
        Examen examen = examenRepository.findById(examenId)
                .orElseThrow(() -> new RuntimeException("Examen no encontrado"));

        if (examen.getAsignatura().getProfesor() == null || !examen.getAsignatura().getProfesor().getId().equals(docenteId)) {
            throw new RuntimeException("No tiene permisos para corregir este examen");
        }

        if (examen.getEstado() != EstadoExamen.ASIGNADO) {
            throw new RuntimeException("El examen no está en estado ASIGNADO");
        }

        List<ExamenPregunta> preguntasExamen = examenPreguntaRepository.findByExamenId(examenId);
        int correctas = 0;
        Random random = new Random();

        for (ExamenPregunta ep : preguntasExamen) {
            List<Respuesta> respuestasPosibles = ep.getPregunta().getRespuestas();
            Respuesta elegida = respuestasPosibles.get(random.nextInt(respuestasPosibles.size()));
            
            examenRespuestaRepository.save(new ExamenRespuesta(examen, ep.getPregunta(), elegida));
            
            if (elegida.isEsCorrecta()) {
                correctas++;
            }
        }

        double nota = (double) correctas / preguntasExamen.size() * 10.0;
        nota = Math.round(nota * 10.0) / 10.0;

        examen.setNotaFinal(nota);
        examen.setEstado(EstadoExamen.CORREGIDO);

        return examenRepository.save(examen);
    }

    private String generarClaveAleatoria() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
