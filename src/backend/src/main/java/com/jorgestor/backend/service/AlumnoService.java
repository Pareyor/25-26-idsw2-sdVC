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
        if (!isValidDniNie(dto.getDni())) {
            throw new RuntimeException("El DNI/NIE no tiene un formato válido");
        }
        if (alumnoRepository.findByDni(dto.getDni()).isPresent()) {
            throw new RuntimeException("El DNI ya está registrado");
        }
        Grado grado = gradoService.findEntityById(dto.getGradoId());
        Alumno alumno = new Alumno(dto.getDni(), dto.getNombre(), dto.getApellidos(), grado);
        Alumno guardado = alumnoRepository.save(alumno);
        return convertToDTO(guardado);
    }

    private boolean isValidDniNie(String dni) {
        return dni != null && dni.matches("^([XYZ]\\d{7}[A-Za-z]|\\d{8}[A-Za-z])$");
    }

    private AlumnoDTO convertToDTO(Alumno alumno) {
        return new AlumnoDTO(
                alumno.getId(),
                alumno.getDni(),
                alumno.getNombre(),
                alumno.getApellidos(),
                alumno.getGrado() != null ? alumno.getGrado().getId() : null
        );
    }
}
