package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.PreguntaDTO;
import com.jorgestor.backend.dto.RespuestaDTO;
import com.jorgestor.backend.model.Asignatura;
import com.jorgestor.backend.model.Pregunta;
import com.jorgestor.backend.model.Respuesta;
import com.jorgestor.backend.model.TipoPregunta;
import com.jorgestor.backend.repository.PreguntaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PreguntaService {

    private final PreguntaRepository preguntaRepository;
    private final AsignaturaService asignaturaService;

    public PreguntaService(PreguntaRepository preguntaRepository, AsignaturaService asignaturaService) {
        this.preguntaRepository = preguntaRepository;
        this.asignaturaService = asignaturaService;
    }

    public List<PreguntaDTO> getAllPreguntas(Long docenteId) {
        return preguntaRepository.findByAsignaturaProfesorId(docenteId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PreguntaDTO> getPreguntasPorAsignatura(Long asignaturaId, Long docenteId) {
        Asignatura asignatura = asignaturaService.findEntityById(asignaturaId);
        if (asignatura.getProfesor() == null || !asignatura.getProfesor().getId().equals(docenteId)) {
            throw new RuntimeException("No tiene permisos para ver preguntas de esta asignatura");
        }
        return preguntaRepository.findByAsignaturaId(asignaturaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PreguntaDTO obtenerPregunta(Long id, Long docenteId) {
        Pregunta p = preguntaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));
        
        if (p.getAsignatura() == null || !p.getAsignatura().getProfesor().getId().equals(docenteId)) {
            throw new RuntimeException("No tiene permisos para ver esta pregunta");
        }
        
        return convertToDTO(p);
    }

    public PreguntaDTO crearPregunta(PreguntaDTO dto) {
        Asignatura asignatura = asignaturaService.findEntityById(dto.getAsignaturaId());
        
        Pregunta pregunta = new Pregunta(
                dto.getEnunciado(),
                dto.getTipo(),
                dto.getTema(),
                dto.getDificultad(),
                asignatura
        );

        if (dto.getRespuestas() != null) {
            List<Respuesta> respuestas = dto.getRespuestas().stream()
                    .map(r -> new Respuesta(r.getOpcion(), r.isEsCorrecta(), pregunta))
                    .collect(Collectors.toList());
            pregunta.setRespuestas(respuestas);
        }

        Pregunta guardada = preguntaRepository.save(pregunta);
        return convertToDTO(guardada);
    }

    public PreguntaDTO actualizarPregunta(Long id, PreguntaDTO dto) {
        Pregunta pregunta = preguntaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));

        pregunta.setEnunciado(dto.getEnunciado());
        pregunta.setTipo(dto.getTipo());
        pregunta.setTema(dto.getTema());
        pregunta.setDificultad(dto.getDificultad());

        if (dto.getAsignaturaId() != null) {
            Asignatura asignatura = asignaturaService.findEntityById(dto.getAsignaturaId());
            pregunta.setAsignatura(asignatura);
        }

        if (dto.getRespuestas() != null) {
            pregunta.getRespuestas().clear();
            List<Respuesta> nuevasRespuestas = dto.getRespuestas().stream()
                    .map(r -> new Respuesta(r.getOpcion(), r.isEsCorrecta(), pregunta))
                    .collect(Collectors.toList());
            pregunta.getRespuestas().addAll(nuevasRespuestas);
        }

        Pregunta guardada = preguntaRepository.save(pregunta);
        return convertToDTO(guardada);
    }

    public void eliminarPregunta(Long id) {
        if (!preguntaRepository.existsById(id)) {
            throw new RuntimeException("Pregunta no encontrada");
        }
        preguntaRepository.deleteById(id);
    }

    public void eliminarTodasPorDocente(Long docenteId) {
        List<Pregunta> preguntas = preguntaRepository.findByAsignaturaProfesorId(docenteId);
        preguntaRepository.deleteAll(preguntas);
    }

    public List<PreguntaDTO> obtenerBancoPreguntas(Long asignaturaId, List<String> temas) {
        return preguntaRepository.findByAsignaturaIdAndTemaIn(asignaturaId, temas).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<String> obtenerTemasPorAsignatura(Long asignaturaId, Long docenteId) {
        Asignatura asignatura = asignaturaService.findEntityById(asignaturaId);
        if (asignatura.getProfesor() == null || !asignatura.getProfesor().getId().equals(docenteId)) {
            throw new RuntimeException("No tiene permisos para acceder a esta asignatura");
        }
        return preguntaRepository.findDistinctTemasByAsignaturaId(asignaturaId);
    }

    private PreguntaDTO convertToDTO(Pregunta pregunta) {
        List<RespuestaDTO> respuestasDTO = pregunta.getRespuestas().stream()
                .map(r -> new RespuestaDTO(r.getId(), r.getOpcion(), r.isEsCorrecta()))
                .collect(Collectors.toList());

        return new PreguntaDTO(
                pregunta.getId(),
                pregunta.getEnunciado(),
                pregunta.getTipo(),
                pregunta.getTema(),
                pregunta.getDificultad(),
                pregunta.getAsignatura() != null ? pregunta.getAsignatura().getId() : null,
                respuestasDTO
        );
    }
}
