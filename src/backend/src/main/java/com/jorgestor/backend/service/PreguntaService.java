package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.PreguntaDTO;
import com.jorgestor.backend.model.Pregunta;
import com.jorgestor.backend.repository.PreguntaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PreguntaService {

    private final PreguntaRepository preguntaRepository;

    public PreguntaService(PreguntaRepository preguntaRepository) {
        this.preguntaRepository = preguntaRepository;
    }

    public List<PreguntaDTO> getAllPreguntas() {
        return preguntaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PreguntaDTO convertToDTO(Pregunta pregunta) {
        return new PreguntaDTO(
                pregunta.getId(),
                pregunta.getEnunciado(),
                pregunta.getTema(),
                pregunta.getDificultad()
        );
    }
}
