package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.AsignaturaDTO;
import com.jorgestor.backend.model.Asignatura;
import com.jorgestor.backend.model.Grado;
import com.jorgestor.backend.repository.AsignaturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignaturaService {

    private final AsignaturaRepository asignaturaRepository;
    private final GradoService gradoService;

    public AsignaturaService(AsignaturaRepository asignaturaRepository, GradoService gradoService) {
        this.asignaturaRepository = asignaturaRepository;
        this.gradoService = gradoService;
    }

    public List<AsignaturaDTO> getAllAsignaturas() {
        return asignaturaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AsignaturaDTO crearAsignatura(AsignaturaDTO dto) {
        if (asignaturaRepository.findByCodigo(dto.getCodigo()).isPresent()) {
            throw new RuntimeException("El código de asignatura ya existe");
        }

        Grado grado = gradoService.findEntityById(dto.getGradoId());
        
        Asignatura asignatura = new Asignatura(
                dto.getCodigo(),
                dto.getTitulo(),
                dto.getCursoAcademico(),
                grado
        );

        Asignatura guardada = asignaturaRepository.save(asignatura);
        return convertToDTO(guardada);
    }

    public AsignaturaDTO obtenerAsignatura(Long id) {
        Asignatura a = asignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));
        return convertToDTO(a);
    }

    public AsignaturaDTO actualizarAsignatura(Long id, AsignaturaDTO dto) {
        Asignatura asignatura = asignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));

        asignatura.setTitulo(dto.getTitulo());
        asignatura.setCodigo(dto.getCodigo());
        asignatura.setCursoAcademico(dto.getCursoAcademico());

        if (dto.getGradoId() != null) {
            Grado grado = gradoService.findEntityById(dto.getGradoId());
            asignatura.setGrado(grado);
        }

        Asignatura guardada = asignaturaRepository.save(asignatura);
        return convertToDTO(guardada);
    }

    private AsignaturaDTO convertToDTO(Asignatura asignatura) {
        return new AsignaturaDTO(
                asignatura.getId(),
                asignatura.getCodigo(),
                asignatura.getTitulo(),
                asignatura.getCursoAcademico(),
                asignatura.getGrado() != null ? asignatura.getGrado().getId() : null
        );
    }
}
