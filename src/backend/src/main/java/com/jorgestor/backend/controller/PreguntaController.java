package com.jorgestor.backend.controller;

import com.jorgestor.backend.dto.PreguntaDTO;
import com.jorgestor.backend.service.PreguntaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/preguntas")
@CrossOrigin(origins = "*")
public class PreguntaController {

    private final PreguntaService preguntaService;

    public PreguntaController(PreguntaService preguntaService) {
        this.preguntaService = preguntaService;
    }

    @GetMapping
    @PreAuthorize("hasRole('DOCENTE')")
    public ResponseEntity<List<PreguntaDTO>> getAllPreguntas() {
        return ResponseEntity.ok(preguntaService.getAllPreguntas());
    }
}
