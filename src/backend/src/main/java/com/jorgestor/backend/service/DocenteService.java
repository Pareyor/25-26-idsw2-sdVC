package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.DocenteDTO;
import com.jorgestor.backend.model.Role;
import com.jorgestor.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocenteService {

    private final UsuarioRepository usuarioRepository;

    public DocenteService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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
}
