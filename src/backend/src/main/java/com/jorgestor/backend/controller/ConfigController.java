package com.jorgestor.backend.controller;

import com.jorgestor.backend.model.Usuario;
import com.jorgestor.backend.repository.UsuarioRepository;
import com.jorgestor.backend.service.ConfigService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    private final ConfigService configService;
    private final UsuarioRepository usuarioRepository;

    public ConfigController(ConfigService configService, UsuarioRepository usuarioRepository) {
        this.configService = configService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/exportar")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<byte[]> exportar() throws IOException {
        byte[] jsonBytes = configService.exportarConfiguracionJson(getCurrentUserId());
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=configuracion.json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonBytes);
    }

    @PostMapping("/importar")
    @PreAuthorize("hasAuthority('ROLE_DOCENTE')")
    public ResponseEntity<Void> importar(@RequestParam("file") MultipartFile file) throws IOException {
        configService.importarConfiguracionJson(file, getCurrentUserId());
        return ResponseEntity.ok().build();
    }

    private Long getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuario.getId();
    }
}
