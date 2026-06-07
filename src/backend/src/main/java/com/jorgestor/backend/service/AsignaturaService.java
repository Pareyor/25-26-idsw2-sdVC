package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.AsignaturaDTO;
import com.jorgestor.backend.model.Asignatura;
import com.jorgestor.backend.model.Grado;
import com.jorgestor.backend.model.Usuario;
import com.jorgestor.backend.repository.AsignaturaRepository;
import com.jorgestor.backend.repository.AlumnoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class AsignaturaService {

    private final AsignaturaRepository asignaturaRepository;
    private final GradoService gradoService;
    private final AlumnoRepository alumnoRepository;

    public AsignaturaService(AsignaturaRepository asignaturaRepository, GradoService gradoService, AlumnoRepository alumnoRepository) {
        this.asignaturaRepository = asignaturaRepository;
        this.gradoService = gradoService;
        this.alumnoRepository = alumnoRepository;
    }

    public List<AsignaturaDTO> getAllAsignaturas(Long docenteId) {
        return asignaturaRepository.findAll().stream()
                .filter(a -> a.getProfesor() != null && a.getProfesor().getId().equals(docenteId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AsignaturaDTO crearAsignatura(AsignaturaDTO dto, Long docenteId) {
        if (asignaturaRepository.findByCodigo(dto.getCodigo()).isPresent()) {
            throw new RuntimeException("El código de asignatura ya existe");
        }

        List<Grado> grados = dto.getGradoIds().stream().map(gradoService::findEntityById).collect(Collectors.toList());
        
        Asignatura asignatura = new Asignatura(
                dto.getCodigo(),
                dto.getTitulo(),
                dto.getCursoAcademico(),
                grados
        );
        
        // Asignar el docente logueado
        Usuario profesor = new Usuario();
        profesor.setId(docenteId);
        asignatura.setProfesor(profesor);

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

        if (dto.getGradoIds() != null) {
            List<Grado> grados = dto.getGradoIds().stream().map(gradoService::findEntityById).collect(Collectors.toList());
            asignatura.setGrados(grados);
        }

        Asignatura guardada = asignaturaRepository.save(asignatura);
        return convertToDTO(guardada);
    }

    public void eliminarAsignatura(Long id) {
        if (!asignaturaRepository.existsById(id)) {
            throw new RuntimeException("Asignatura no encontrada");
        }
        asignaturaRepository.deleteById(id);
    }

    public Asignatura findEntityById(Long id) {
        return asignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));
    }

    private AsignaturaDTO convertToDTO(Asignatura asignatura) {
        Map<Long, Integer> alumnosPorGrado = new HashMap<>();
        List<Long> gradoIds = new ArrayList<>();
        
        for (Grado grado : asignatura.getGrados()) {
            gradoIds.add(grado.getId());
            int numAlumnos = (int) alumnoRepository.countByGradoId(grado.getId());
            alumnosPorGrado.put(grado.getId(), numAlumnos);
        }

        return new AsignaturaDTO(
                asignatura.getId(),
                asignatura.getCodigo(),
                asignatura.getTitulo(),
                asignatura.getCursoAcademico(),
                gradoIds,
                asignatura.getProfesor() != null ? asignatura.getProfesor().getId() : null,
                alumnosPorGrado
        );
    }
}
