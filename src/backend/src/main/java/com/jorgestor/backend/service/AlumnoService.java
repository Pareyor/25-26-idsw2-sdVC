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

    public List<AlumnoDTO> obtenerAlumnosPorGrado(Long gradoId, Long docenteId) {
        Grado grado = gradoService.findEntityById(gradoId);
        boolean tieneAcceso = grado.getAsignaturas().stream()
                .anyMatch(asig -> asig.getProfesor() != null && asig.getProfesor().getId().equals(docenteId));
        
        if (!tieneAcceso) {
            throw new RuntimeException("No tiene permisos para ver alumnos de este grado");
        }
        
        return alumnoRepository.findByGradoId(gradoId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AlumnoDTO> getAllAlumnos(Long docenteId) {
        return alumnoRepository.findByGradoAsignaturasProfesorId(docenteId).stream()
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
        Alumno alumno = new Alumno(dto.getDni(), dto.getNombre(), dto.getApellidos(), grado, dto.getCurso());
        Alumno guardado = alumnoRepository.save(alumno);
        return convertToDTO(guardado);
    }

    private boolean isValidDniNie(String dni) {
        return dni != null && dni.matches("^([XYZ]\\d{7}[A-Za-z]|\\d{8}[A-Za-z])$");
    }

    public AlumnoDTO obtenerAlumno(Long id, Long docenteId) {
        Alumno a = alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        
        // Verificar que el alumno pertenece al grado de una asignatura del docente
        boolean tieneAcceso = a.getGrado().getAsignaturas().stream()
                .anyMatch(asig -> asig.getProfesor() != null && asig.getProfesor().getId().equals(docenteId));
        
        if (!tieneAcceso) {
            throw new RuntimeException("No tiene permisos para ver este alumno");
        }
        
        return convertToDTO(a);
    }

    public AlumnoDTO actualizarAlumno(Long id, AlumnoDTO dto) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        if (!alumno.getDni().equals(dto.getDni())) {
            if (alumnoRepository.findByDni(dto.getDni()).isPresent()) {
                throw new RuntimeException("El DNI ya está registrado para otro alumno");
            }
            alumno.setDni(dto.getDni());
        }

        alumno.setNombre(dto.getNombre());
        alumno.setApellidos(dto.getApellidos());
        alumno.setCurso(dto.getCurso());

        if (dto.getGradoId() != null) {
            Grado grado = gradoService.findEntityById(dto.getGradoId());
            alumno.setGrado(grado);
        }

        Alumno guardado = alumnoRepository.save(alumno);
        return convertToDTO(guardado);
    }

    public void eliminarAlumno(Long id) {
        if (!alumnoRepository.existsById(id)) {
            throw new RuntimeException("Alumno no encontrado");
        }
        alumnoRepository.deleteById(id);
    }

    private AlumnoDTO convertToDTO(Alumno alumno) {
        return new AlumnoDTO(
                alumno.getId(),
                alumno.getDni(),
                alumno.getNombre(),
                alumno.getApellidos(),
                alumno.getGrado() != null ? alumno.getGrado().getId() : null,
                alumno.getCurso()
        );
    }
}
