package com.jorgestor.backend.controller;

import com.jorgestor.backend.dto.AsignaturaDTO;
import com.jorgestor.backend.model.Usuario;
import com.jorgestor.backend.repository.UsuarioRepository;
import com.jorgestor.backend.service.AsignaturaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaturas")
public class AsignaturaController {

    private final AsignaturaService asignaturaService;
    private final UsuarioRepository usuarioRepository;

    public AsignaturaController(AsignaturaService asignaturaService, UsuarioRepository usuarioRepository) {
        this.asignaturaService = asignaturaService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public List<AsignaturaDTO> getAllAsignaturas() {
        return asignaturaService.getAllAsignaturas(getCurrentUserId());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<AsignaturaDTO> createAsignatura(@RequestBody AsignaturaDTO asignaturaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(asignaturaService.crearAsignatura(asignaturaDTO, getCurrentUserId()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<AsignaturaDTO> getAsignatura(@PathVariable Long id) {
        return ResponseEntity.ok(asignaturaService.obtenerAsignatura(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<AsignaturaDTO> updateAsignatura(@PathVariable Long id, @RequestBody AsignaturaDTO asignaturaDTO) {
        return ResponseEntity.ok(asignaturaService.actualizarAsignatura(id, asignaturaDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public void deleteAsignatura(@PathVariable Long id) {
        asignaturaService.eliminarAsignatura(id);
    }
    
    private Long getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuario.getId();
    }
}
