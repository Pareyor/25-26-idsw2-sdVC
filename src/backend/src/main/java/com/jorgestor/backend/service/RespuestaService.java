package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.RespuestaDTO;
import com.jorgestor.backend.model.Pregunta;
import com.jorgestor.backend.model.Respuesta;
import com.jorgestor.backend.repository.RespuestaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final PreguntaService preguntaService;

    public RespuestaService(RespuestaRepository respuestaRepository, PreguntaService preguntaService) {
        this.respuestaRepository = respuestaRepository;
        this.preguntaService = preguntaService;
    }

    public List<RespuestaDTO> obtenerRespuestasPorPregunta(Long preguntaId) {
        // En un entorno real validaríamos propiedad aquí, pero seguimos la política simplificada del proyecto
        return respuestaRepository.findByPreguntaId(preguntaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RespuestaDTO actualizarRespuesta(Long id, RespuestaDTO dto) {
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada"));

        respuesta.setOpcion(dto.getOpcion());
        respuesta.setEsCorrecta(dto.isEsCorrecta());

        Respuesta guardada = respuestaRepository.save(respuesta);
        return convertToDTO(guardada);
    }

    private RespuestaDTO convertToDTO(Respuesta respuesta) {
        return new RespuestaDTO(
                respuesta.getId(),
                respuesta.getOpcion(),
                respuesta.isEsCorrecta()
        );
    }
}
