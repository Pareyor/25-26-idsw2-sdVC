package com.jorgestor.backend.controller;

import com.jorgestor.backend.dto.DocenteDTO;
import com.jorgestor.backend.service.DocenteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
public class DocenteController {

    private final DocenteService docenteService;

    public DocenteController(DocenteService docenteService) {
        this.docenteService = docenteService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<DocenteDTO> getDocentes() {
        return docenteService.listarDocentes();
    }
}
