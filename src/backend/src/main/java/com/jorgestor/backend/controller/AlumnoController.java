package com.jorgestor.backend.controller;

import com.jorgestor.backend.dto.AlumnoDTO;
import com.jorgestor.backend.model.Usuario;
import com.jorgestor.backend.repository.UsuarioRepository;
import com.jorgestor.backend.service.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    private final AlumnoService alumnoService;
    private final UsuarioRepository usuarioRepository;

    public AlumnoController(AlumnoService alumnoService, UsuarioRepository usuarioRepository) {
        this.alumnoService = alumnoService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<List<AlumnoDTO>> getAllAlumnos() {
        return ResponseEntity.ok(alumnoService.getAllAlumnos(getCurrentUserId()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<AlumnoDTO> getAlumno(@PathVariable Long id) {
        return ResponseEntity.ok(alumnoService.obtenerAlumno(id, getCurrentUserId()));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<AlumnoDTO> createAlumno(@RequestBody AlumnoDTO alumnoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.crearAlumno(alumnoDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<AlumnoDTO> updateAlumno(@PathVariable Long id, @RequestBody AlumnoDTO alumnoDTO) {
        return ResponseEntity.ok(alumnoService.actualizarAlumno(id, alumnoDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public void deleteAlumno(@PathVariable Long id) {
        alumnoService.eliminarAlumno(id);
    }
    
    @GetMapping("/grado/{gradoId}")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<List<AlumnoDTO>> getAlumnosByGrado(@PathVariable Long gradoId) {
        return ResponseEntity.ok(alumnoService.obtenerAlumnosPorGrado(gradoId, getCurrentUserId()));
    }
    
    private Long getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuario.getId();
    }
}
