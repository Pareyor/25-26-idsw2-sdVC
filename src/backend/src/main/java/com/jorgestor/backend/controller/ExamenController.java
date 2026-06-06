package com.jorgestor.backend.controller;

import com.jorgestor.backend.dto.GenerarExamenesDTO;
import com.jorgestor.backend.dto.GeneracionExitoDTO;
import com.jorgestor.backend.model.Usuario;
import com.jorgestor.backend.repository.UsuarioRepository;
import com.jorgestor.backend.service.ExamenService;
import com.jorgestor.backend.service.ExamenSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/examenes")
@CrossOrigin(origins = "*")
public class ExamenController {

    private final ExamenService examenService;
    private final ExamenSessionService sessionService;
    private final UsuarioRepository usuarioRepository;

    public ExamenController(ExamenService examenService, ExamenSessionService sessionService, UsuarioRepository usuarioRepository) {
        this.examenService = examenService;
        this.sessionService = sessionService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/generar")
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<GeneracionExitoDTO> generarExamenes(@RequestBody GenerarExamenesDTO dto) {
        Long docenteId = getCurrentUserId();
        return ResponseEntity.ok(examenService.generarExamenes(dto, docenteId));
    }

    @DeleteMapping("/generar/cancelar")
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<Void> cancelarGeneracion() {
        sessionService.limpiarBorradores();
        return ResponseEntity.noContent().build();
    }

    private Long getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuario.getId();
    }
}
