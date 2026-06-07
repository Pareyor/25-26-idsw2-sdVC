package com.jorgestor.backend.controller;

import com.jorgestor.backend.dto.GenerarExamenesDTO;
import com.jorgestor.backend.dto.GeneracionExitoDTO;
import com.jorgestor.backend.dto.PlantillaExamenDTO;
import com.jorgestor.backend.dto.AsignarExamenesDTO;
import com.jorgestor.backend.dto.ExamenBorradorDTO;
import com.jorgestor.backend.model.Asignatura;
import com.jorgestor.backend.model.Grado;
import com.jorgestor.backend.model.Examen;
import com.jorgestor.backend.model.ExamenBorrador;
import com.jorgestor.backend.model.Usuario;
import com.jorgestor.backend.repository.UsuarioRepository;
import com.jorgestor.backend.service.ExamenService;
import com.jorgestor.backend.service.AsignaturaService;
import com.jorgestor.backend.repository.ExamenBorradorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/examenes")
@CrossOrigin(origins = "*")
public class ExamenController {

    private final ExamenService examenService;
    private final ExamenBorradorRepository borradorRepository;
    private final UsuarioRepository usuarioRepository;
    private final AsignaturaService asignaturaService;

    public ExamenController(ExamenService examenService, ExamenBorradorRepository borradorRepository, UsuarioRepository usuarioRepository, AsignaturaService asignaturaService) {
        this.examenService = examenService;
        this.borradorRepository = borradorRepository;
        this.usuarioRepository = usuarioRepository;
        this.asignaturaService = asignaturaService;
    }

    @PostMapping("/generar")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<GeneracionExitoDTO> generarExamenes(@RequestBody GenerarExamenesDTO dto) {
        Long docenteId = getCurrentUserId();
        return ResponseEntity.ok(examenService.generarExamenes(dto, docenteId));
    }

    @GetMapping("/generar/borradores")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<List<ExamenBorradorDTO>> obtenerBorradores() {
        List<ExamenBorradorDTO> dtos = borradorRepository.findAll().stream()
                .map(b -> new ExamenBorradorDTO(
                        b.getId(),
                        b.getAsignatura().getId(),
                        b.getGrado() != null ? b.getGrado().getId() : null,
                        b.getTipoExamen(),
                        b.getClave()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/generar/cancelar")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<Void> cancelarGeneracion() {
        borradorRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/asignar")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<Void> asignarExamenes(@RequestBody AsignarExamenesDTO dto) {
        if (dto.getAlumnoIds() == null || dto.getAlumnoIds().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        examenService.persistirAsignaciones(dto.getAlumnoIds());
        
        return ResponseEntity.ok().build();
    }

    @GetMapping("/corregir/listar")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<List<Map<String, Object>>> obtenerExamenesParaCorregir() {
        Long docenteId = getCurrentUserId();
        List<Examen> examenes = examenService.obtenerExamenesParaCorregir(docenteId);
        List<Map<String, Object>> response = examenes.stream().map(e -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", e.getId());
            map.put("alumno", e.getAlumno().getNombre() + " " + e.getAlumno().getApellidos());
            map.put("asignatura", e.getAsignatura().getTitulo());
            map.put("tipo", e.getTipoExamen());
            return map;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/corregir/{examenId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<Map<String, Object>> corregirExamen(@PathVariable Long examenId) {
        Long docenteId = getCurrentUserId();
        Examen examen = examenService.corregirExamen(examenId, docenteId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("notaFinal", examen.getNotaFinal());
        response.put("estado", examen.getEstado());
        
        return ResponseEntity.ok(response);
    }

    private Long getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuario.getId();
    }
}
