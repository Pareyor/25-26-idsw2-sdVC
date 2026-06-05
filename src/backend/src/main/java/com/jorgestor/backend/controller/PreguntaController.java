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
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<List<PreguntaDTO>> getAllPreguntas() {
        return ResponseEntity.ok(preguntaService.getAllPreguntas());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<PreguntaDTO> createPregunta(@RequestBody PreguntaDTO preguntaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(preguntaService.crearPregunta(preguntaDTO));
    }
}
