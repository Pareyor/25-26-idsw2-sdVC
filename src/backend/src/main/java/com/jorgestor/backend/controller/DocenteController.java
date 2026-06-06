package com.jorgestor.backend.controller;

import com.jorgestor.backend.dto.DocenteDTO;
import com.jorgestor.backend.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
public class DocenteController {

    private final UsuarioService usuarioService;

    public DocenteController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCENTE')")
    public List<DocenteDTO> getDocentes() {
        return usuarioService.listarDocentes();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public DocenteDTO createDocente(@RequestBody DocenteDTO docenteDTO) {
        return usuarioService.crearDocente(docenteDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DocenteDTO getDocente(@PathVariable Long id) {
        return usuarioService.obtenerDocente(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DocenteDTO updateDocente(@PathVariable Long id, @RequestBody DocenteDTO docenteDTO) {
        return usuarioService.actualizarDocente(id, docenteDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteDocente(@PathVariable Long id) {
        usuarioService.eliminarDocente(id);
    }
}
