package com.jorgestor.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorgestor.backend.dto.*;
import com.jorgestor.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ConfigService {

    private final AsignaturaService asignaturaService;
    private final PreguntaService preguntaService;
    private final ObjectMapper objectMapper;

    public ConfigService(AsignaturaService asignaturaService, PreguntaService preguntaService, ObjectMapper objectMapper) {
        this.asignaturaService = asignaturaService;
        this.preguntaService = preguntaService;
        this.objectMapper = objectMapper;
    }

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConfigService.class);

    public byte[] exportarConfiguracionJson(Long docenteId) throws IOException {
        logger.info("DEBUG - Exportando configuración para docenteId: {}", docenteId);
        List<AsignaturaDTO> asignaturas = asignaturaService.getAllAsignaturas(docenteId);
        List<PreguntaDTO> preguntas = preguntaService.getAllPreguntas(docenteId);
        ConfigExportDTO config = new ConfigExportDTO(asignaturas, preguntas);
        logger.info("DEBUG - Asignaturas a exportar: {}, Preguntas: {}", asignaturas.size(), preguntas.size());
        
        return objectMapper.writeValueAsBytes(config);
    }

    @Transactional
    public void importarConfiguracionJson(MultipartFile file, Long docenteId) throws IOException {
        logger.info("DEBUG - Archivo recibido: {}, tamaño: {}", file.getOriginalFilename(), file.getSize());

        ConfigExportDTO config = objectMapper.readValue(file.getInputStream(), ConfigExportDTO.class);
        
        // Mapa para mapear ID antiguo (JSON) -> ID nuevo (BD)
        java.util.Map<Long, Long> idMap = new java.util.HashMap<>();

        logger.info("DEBUG - Iniciando limpieza para docenteId: {}", docenteId);
        preguntaService.eliminarTodasPorDocente(docenteId);
        asignaturaService.eliminarTodasPorDocente(docenteId);

        // Importar Asignaturas y guardar mapeo
        if (config.getAsignaturas() != null) {
            for (AsignaturaDTO dto : config.getAsignaturas()) {
                Long idAntiguo = dto.getId();
                // Nota: crearAsignatura devuelve DTO con el nuevo ID
                AsignaturaDTO guardada = asignaturaService.crearAsignatura(dto, docenteId);
                idMap.put(idAntiguo, guardada.getId());
                logger.info("DEBUG - Mapeado asignatura: {} -> {}", idAntiguo, guardada.getId());
            }
        }

        // Importar Preguntas usando el mapeo
        if (config.getPreguntas() != null) {
            for (PreguntaDTO dto : config.getPreguntas()) {
                Long nuevoIdAsignatura = idMap.get(dto.getAsignaturaId());
                if (nuevoIdAsignatura != null) {
                    dto.setAsignaturaId(nuevoIdAsignatura);
                    preguntaService.crearPregunta(dto);
                } else {
                    logger.warn("DEBUG - No se encontró mapeo para asignaturaId: {}. Saltando pregunta.", dto.getAsignaturaId());
                }
            }
        }
        logger.info("DEBUG - Importación finalizada");
    }
}
