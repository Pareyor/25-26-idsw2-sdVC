package com.jorgestor.backend.controller;

import com.jorgestor.backend.dto.AlumnoDTO;
import com.jorgestor.backend.service.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<List<AlumnoDTO>> getAllAlumnos() {
        System.out.println("Accediendo a listar alumnos...");
        return ResponseEntity.ok(alumnoService.getAllAlumnos());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<AlumnoDTO> createAlumno(@RequestBody AlumnoDTO alumnoDTO) {
        System.out.println("Creando alumno...");
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.crearAlumno(alumnoDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<AlumnoDTO> getAlumno(@PathVariable Long id) {
        return ResponseEntity.ok(alumnoService.obtenerAlumno(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<AlumnoDTO> updateAlumno(@PathVariable Long id, @RequestBody AlumnoDTO alumnoDTO) {
        return ResponseEntity.ok(alumnoService.actualizarAlumno(id, alumnoDTO));
    }
}
