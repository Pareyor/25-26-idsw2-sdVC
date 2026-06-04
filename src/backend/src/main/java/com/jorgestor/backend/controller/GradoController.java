package com.jorgestor.backend.controller;

import com.jorgestor.backend.dto.GradoDTO;
import com.jorgestor.backend.service.GradoService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grados")
public class GradoController {

    private final GradoService gradoService;

    public GradoController(GradoService gradoService) {
        this.gradoService = gradoService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE')")
    public List<GradoDTO> getGrados() {
        return gradoService.listarGrados();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('DOCENTE')")
    public GradoDTO createGrado(@RequestBody GradoDTO gradoDTO) {
        return gradoService.crearGrado(gradoDTO);
    }
}
