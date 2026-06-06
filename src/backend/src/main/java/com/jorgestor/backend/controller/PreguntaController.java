package com.jorgestor.backend.controller;

import com.jorgestor.backend.dto.PreguntaDTO;
import com.jorgestor.backend.service.PreguntaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preguntas")
public class PreguntaController {

    private final PreguntaService preguntaService;

    public PreguntaController(PreguntaService preguntaService) {
        this.preguntaService = preguntaService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<List<PreguntaDTO>> getAllPreguntas() {
        return ResponseEntity.ok(preguntaService.getAllPreguntas());
    }

    @GetMapping("/asignatura/{asignaturaId}/temas")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<List<String>> getTemasByAsignatura(@PathVariable Long asignaturaId) {
        return ResponseEntity.ok(preguntaService.obtenerTemasPorAsignatura(asignaturaId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<PreguntaDTO> createPregunta(@RequestBody PreguntaDTO preguntaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(preguntaService.crearPregunta(preguntaDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<PreguntaDTO> getPregunta(@PathVariable Long id) {
        return ResponseEntity.ok(preguntaService.obtenerPregunta(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<PreguntaDTO> updatePregunta(@PathVariable Long id, @RequestBody PreguntaDTO preguntaDTO) {
        return ResponseEntity.ok(preguntaService.actualizarPregunta(id, preguntaDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_DOCENTE')")
    public void deletePregunta(@PathVariable Long id) {
        preguntaService.eliminarPregunta(id);
    }
}
