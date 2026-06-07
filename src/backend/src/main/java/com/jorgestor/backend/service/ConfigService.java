package com.jorgestor.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorgestor.backend.dto.*;
import com.jorgestor.backend.model.*;
import com.jorgestor.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigService {

    private final AsignaturaService asignaturaService;
    private final PreguntaService preguntaService;
    private final AsignaturaRepository asignaturaRepository;
    private final PreguntaRepository preguntaRepository;
    private final RespuestaRepository respuestaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public ConfigService(AsignaturaService asignaturaService, PreguntaService preguntaService,
                         AsignaturaRepository asignaturaRepository, PreguntaRepository preguntaRepository,
                         RespuestaRepository respuestaRepository, UsuarioRepository usuarioRepository,
                         ObjectMapper objectMapper) {
        this.asignaturaService = asignaturaService;
        this.preguntaService = preguntaService;
        this.asignaturaRepository = asignaturaRepository;
        this.preguntaRepository = preguntaRepository;
        this.respuestaRepository = respuestaRepository;
        this.usuarioRepository = usuarioRepository;
        this.objectMapper = objectMapper;
    }

    public byte[] exportarConfiguracionJson(Long docenteId) throws IOException {
        List<AsignaturaDTO> asignaturas = asignaturaService.getAllAsignaturas(docenteId);
        List<PreguntaDTO> preguntas = preguntaService.getAllPreguntas(docenteId);
        ConfigExportDTO config = new ConfigExportDTO(asignaturas, preguntas);
        
        return objectMapper.writeValueAsBytes(config);
    }

    public void importarConfiguracionJson(MultipartFile file, Long docenteId) throws IOException {
        ConfigExportDTO config = objectMapper.readValue(file.getInputStream(), ConfigExportDTO.class);
        Usuario docente = usuarioRepository.findById(docenteId).orElseThrow();

        // Importar Asignaturas
        for (AsignaturaDTO dto : config.getAsignaturas()) {
            Asignatura asig = asignaturaRepository.findByCodigo(dto.getCodigo())
                    .orElse(new Asignatura(dto.getCodigo(), dto.getTitulo(), "2025-2026", new ArrayList<>()));
            
            asig.setTitulo(dto.getTitulo());
            asig.setProfesor(docente);
            asignaturaRepository.save(asig);
        }

        // Importar Preguntas
        List<AsignaturaDTO> asignaturasDocente = asignaturaService.getAllAsignaturas(docenteId);
        if (!asignaturasDocente.isEmpty()) {
            Asignatura targetAsig = asignaturaRepository.findByCodigo(asignaturasDocente.get(0).getCodigo()).orElseThrow();
            for (PreguntaDTO dto : config.getPreguntas()) {
                Pregunta p = new Pregunta(dto.getEnunciado(), TipoPregunta.TEORIA, dto.getTema(), DificultadPregunta.FACIL, targetAsig);
                Pregunta savedP = preguntaRepository.save(p);
                for (RespuestaDTO respDto : dto.getRespuestas()) {
                    respuestaRepository.save(new Respuesta(respDto.getOpcion(), respDto.isEsCorrecta(), savedP));
                }
            }
        }
    }
}
