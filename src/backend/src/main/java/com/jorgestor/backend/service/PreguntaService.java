package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.PreguntaDTO;
import com.jorgestor.backend.dto.RespuestaDTO;
import com.jorgestor.backend.model.Asignatura;
import com.jorgestor.backend.model.Pregunta;
import com.jorgestor.backend.model.Respuesta;
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

    public List<PreguntaDTO> getAllPreguntas() {
        return preguntaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PreguntaDTO crearPregunta(PreguntaDTO dto) {
        Asignatura asignatura = asignaturaService.findEntityById(dto.getAsignaturaId());
        
        Pregunta pregunta = new Pregunta(
                dto.getEnunciado(),
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

    private PreguntaDTO convertToDTO(Pregunta pregunta) {
        List<RespuestaDTO> respuestasDTO = pregunta.getRespuestas().stream()
                .map(r -> new RespuestaDTO(r.getId(), r.getOpcion(), r.isEsCorrecta()))
                .collect(Collectors.toList());

        return new PreguntaDTO(
                pregunta.getId(),
                pregunta.getEnunciado(),
                pregunta.getTema(),
                pregunta.getDificultad(),
                pregunta.getAsignatura() != null ? pregunta.getAsignatura().getId() : null,
                respuestasDTO
        );
    }
}
