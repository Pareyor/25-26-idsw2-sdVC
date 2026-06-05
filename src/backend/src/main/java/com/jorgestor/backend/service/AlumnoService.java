package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.AlumnoDTO;
import com.jorgestor.backend.model.Alumno;
import com.jorgestor.backend.model.Grado;
import com.jorgestor.backend.repository.AlumnoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final GradoService gradoService;

    public AlumnoService(AlumnoRepository alumnoRepository, GradoService gradoService) {
        this.alumnoRepository = alumnoRepository;
        this.gradoService = gradoService;
    }

    public List<AlumnoDTO> getAllAlumnos() {
        return alumnoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AlumnoDTO crearAlumno(AlumnoDTO dto) {
        if (alumnoRepository.findByNiu(dto.getNiu()).isPresent()) {
            throw new RuntimeException("El NIU ya está registrado");
        }
        Grado grado = gradoService.findEntityById(dto.getGradoId());
        Alumno alumno = new Alumno(dto.getNiu(), dto.getNombre(), dto.getApellidos(), grado);
        Alumno guardado = alumnoRepository.save(alumno);
        return convertToDTO(guardado);
    }

    private AlumnoDTO convertToDTO(Alumno alumno) {
        return new AlumnoDTO(
                alumno.getId(),
                alumno.getNiu(),
                alumno.getNombre(),
                alumno.getApellidos(),
                alumno.getGrado() != null ? alumno.getGrado().getId() : null
        );
    }
}
