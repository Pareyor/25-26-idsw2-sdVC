package com.jorgestor.backend.controller;

import com.jorgestor.backend.dto.GenerarExamenesDTO;
import com.jorgestor.backend.dto.GeneracionExitoDTO;
import com.jorgestor.backend.dto.AsignarExamenesDTO;
import com.jorgestor.backend.dto.ExamenBorradorDTO;
import com.jorgestor.backend.dto.DetalleExamenDTO;
import com.jorgestor.backend.model.Examen;
import com.jorgestor.backend.model.Usuario;
import com.jorgestor.backend.repository.UsuarioRepository;
import com.jorgestor.backend.service.ExamenService;
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

    public ExamenController(ExamenService examenService, ExamenBorradorRepository borradorRepository, UsuarioRepository usuarioRepository) {
        this.examenService = examenService;
        this.borradorRepository = borradorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/generar")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<GeneracionExitoDTO> generarExamenes(@RequestBody GenerarExamenesDTO dto) {
        Long docenteId = getCurrentUserId();
        return ResponseEntity.ok(examenService.generarExamenes(dto, docenteId));
    }

    @GetMapping("/generar/borradores")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<List<ExamenBorradorDTO>> obtenerBorradores() {
        List<ExamenBorradorDTO> dtos = borradorRepository.findAll().stream()
                .map(b -> new ExamenBorradorDTO(
                        b.getId(),
                        b.getAsignatura().getId(),
                        b.getGrado() != null ? b.getGrado().getId() : null,
                        b.getTipoExamen(),
                        b.getClave(),
                        b.getPreguntas() != null ? b.getPreguntas().size() : 0
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/generar/cancelar")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<Void> cancelarGeneracion() {
        borradorRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/asignar")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<Void> asignarExamenes(@RequestBody AsignarExamenesDTO dto) {
        if (dto.getAlumnoIds() == null || dto.getAlumnoIds().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        examenService.persistirAsignaciones(dto.getAlumnoIds());
        
        return ResponseEntity.ok().build();
    }

    @GetMapping("/corregir/listar")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<List<Map<String, Object>>> obtenerExamenesParaCorregir() {
        Long docenteId = getCurrentUserId();
        List<Examen> examenes = examenService.obtenerTodosExamenesDocente(docenteId);
        List<Map<String, Object>> response = examenes.stream().map(e -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", e.getId());
            map.put("alumno", e.getAlumno().getNombre() + " " + e.getAlumno().getApellidos());
            map.put("grado", e.getAlumno().getGrado().getTitulo());
            map.put("asignatura", e.getAsignatura().getTitulo());
            map.put("tipo", e.getTipoExamen());
            map.put("estado", e.getEstado());
            map.put("notaFinal", e.getNotaFinal());
            return map;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/corregir/todos")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<Void> corregirTodos() {
        Long docenteId = getCurrentUserId();
        examenService.corregirTodosExamenes(docenteId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/corregir/asignatura/{asignaturaId}")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<Void> corregirPorAsignatura(@PathVariable Long asignaturaId) {
        Long docenteId = getCurrentUserId();
        examenService.corregirExamenesPorAsignatura(asignaturaId, docenteId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/detalle/{examenId}")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<DetalleExamenDTO> obtenerDetalleExamen(@PathVariable Long examenId) {
        Long docenteId = getCurrentUserId();
        return ResponseEntity.ok(examenService.obtenerDetalleExamen(examenId, docenteId));
    }

    @GetMapping("/alumno/{alumnoId}/corregidos")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<List<Map<String, Object>>> obtenerExamenesCorregidosPorAlumno(@PathVariable Long alumnoId) {
        List<Examen> examenes = examenService.obtenerExamenesCorregidosPorAlumno(alumnoId);
        List<Map<String, Object>> response = examenes.stream().map(e -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", e.getId());
            map.put("asignatura", e.getAsignatura().getTitulo());
            map.put("tipo", e.getTipoExamen());
            map.put("notaFinal", e.getNotaFinal());
            return map;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/detalle-borrador/{borradorId}")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<DetalleExamenDTO> obtenerDetalleBorrador(@PathVariable Long borradorId) {
        Long docenteId = getCurrentUserId();
        return ResponseEntity.ok(examenService.obtenerDetalleBorrador(borradorId, docenteId));
    }

    @PostMapping("/corregir/{examenId}")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
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
