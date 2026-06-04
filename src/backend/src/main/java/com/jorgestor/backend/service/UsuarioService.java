package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.DocenteDTO;
import com.jorgestor.backend.model.Role;
import com.jorgestor.backend.model.Usuario;
import com.jorgestor.backend.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<DocenteDTO> listarDocentes() {
        return usuarioRepository.findByRole(Role.ROLE_DOCENTE).stream()
                .map(u -> new DocenteDTO(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getNombre(),
                        u.getApellidos()
                ))
                .collect(Collectors.toList());
    }

    public DocenteDTO crearDocente(DocenteDTO dto) {
        if (usuarioRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("El DNI ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setEmail(dto.getEmail());
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setRole(Role.ROLE_DOCENTE);
        
        // Password por defecto: el mismo username (DNI)
        usuario.setPassword(passwordEncoder.encode(dto.getUsername()));

        Usuario guardado = usuarioRepository.save(usuario);
        dto.setId(guardado.getId());
        return dto;
    }
}
