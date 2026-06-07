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
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public List<GradoDTO> getGrados() {
        return gradoService.listarGrados();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public GradoDTO getGrado(@PathVariable Long id) {
        return gradoService.obtenerGrado(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public GradoDTO createGrado(@RequestBody GradoDTO gradoDTO) {
        return gradoService.crearGrado(gradoDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public GradoDTO updateGrado(@PathVariable Long id, @RequestBody GradoDTO gradoDTO) {
        return gradoService.actualizarGrado(id, gradoDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public void deleteGrado(@PathVariable Long id) {
        gradoService.eliminarGrado(id);
    }
}
