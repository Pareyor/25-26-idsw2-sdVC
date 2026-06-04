package com.jorgestor.backend.controller;

import com.jorgestor.backend.dto.AsignaturaDTO;
import com.jorgestor.backend.service.AsignaturaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaturas")
public class AsignaturaController {

    private final AsignaturaService asignaturaService;

    public AsignaturaController(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public List<AsignaturaDTO> getAllAsignaturas() {
        return asignaturaService.getAllAsignaturas();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<AsignaturaDTO> createAsignatura(@RequestBody AsignaturaDTO asignaturaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(asignaturaService.crearAsignatura(asignaturaDTO));
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
}
