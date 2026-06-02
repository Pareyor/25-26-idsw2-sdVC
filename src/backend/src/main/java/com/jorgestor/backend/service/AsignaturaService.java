package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.AsignaturaDTO;
import com.jorgestor.backend.model.Asignatura;
import com.jorgestor.backend.repository.AsignaturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsignaturaService {

    private final AsignaturaRepository asignaturaRepository;

    public AsignaturaService(AsignaturaRepository asignaturaRepository) {
        this.asignaturaRepository = asignaturaRepository;
    }

    public List<AsignaturaDTO> getAllAsignaturas() {
        return asignaturaRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AsignaturaDTO convertToDTO(Asignatura asignatura) {
        return new AsignaturaDTO(
                asignatura.getId(),
                asignatura.getCodigo(),
                asignatura.getTitulo(),
                asignatura.getCursoAcademico()
        );
    }
}
