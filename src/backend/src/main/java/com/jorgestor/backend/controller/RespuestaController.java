package com.jorgestor.backend.controller;

import com.jorgestor.backend.dto.RespuestaDTO;
import com.jorgestor.backend.service.RespuestaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/respuestas")
public class RespuestaController {

    private final RespuestaService respuestaService;

    public RespuestaController(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @GetMapping("/pregunta/{preguntaId}")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<List<RespuestaDTO>> getRespuestasPorPregunta(@PathVariable Long preguntaId) {
        return ResponseEntity.ok(respuestaService.obtenerRespuestasPorPregunta(preguntaId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<RespuestaDTO> updateRespuesta(@PathVariable Long id, @RequestBody RespuestaDTO respuestaDTO) {
        return ResponseEntity.ok(respuestaService.actualizarRespuesta(id, respuestaDTO));
    }
}
