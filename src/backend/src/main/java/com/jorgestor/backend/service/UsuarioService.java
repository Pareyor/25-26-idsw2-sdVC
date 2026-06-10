package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.DocenteDTO;
import com.jorgestor.backend.model.Asignatura;
import com.jorgestor.backend.model.Grado;
import com.jorgestor.backend.model.Role;
import com.jorgestor.backend.model.Usuario;
import com.jorgestor.backend.repository.AsignaturaRepository;
import com.jorgestor.backend.repository.GradoRepository;
import com.jorgestor.backend.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AsignaturaRepository asignaturaRepository;
    private final GradoRepository gradoRepository;
    private final PreguntaService preguntaService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, 
                          AsignaturaRepository asignaturaRepository, 
                          GradoRepository gradoRepository,
                          PreguntaService preguntaService,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.asignaturaRepository = asignaturaRepository;
        this.gradoRepository = gradoRepository;
        this.preguntaService = preguntaService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<DocenteDTO> listarDocentes() {
        return usuarioRepository.findByRole(Role.ROLE_DOCENTE).stream()
                .map(u -> new DocenteDTO(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getNombre(),
                        u.getApellidos(),
                        null
                ))
                .collect(Collectors.toList());
    }

    public DocenteDTO obtenerDocente(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        
        return new DocenteDTO(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getNombre(),
                u.getApellidos(),
                null
        );
    }

    public DocenteDTO crearDocente(DocenteDTO dto) {
        if (usuarioRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("El DNI/Usuario ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setEmail(dto.getEmail());
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());
        usuario.setRole(Role.ROLE_DOCENTE);

        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));

        Usuario guardado = usuarioRepository.save(usuario);
        dto.setId(guardado.getId());
        dto.setPassword(null); // No devolver contraseña
        return dto;
    }

    public DocenteDTO actualizarDocente(Long id, DocenteDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        usuario.setUsername(dto.getUsername());
        usuario.setEmail(dto.getEmail());
        usuario.setNombre(dto.getNombre());
        usuario.setApellidos(dto.getApellidos());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        Usuario guardado = usuarioRepository.save(usuario);
        return new DocenteDTO(
                guardado.getId(),
                guardado.getUsername(),
                guardado.getEmail(),
                guardado.getNombre(),
                guardado.getApellidos(),
                null
        );
    }

    @Transactional
    public void eliminarDocente(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        // 1. Eliminar preguntas (y sus respuestas por cascada)
        preguntaService.eliminarTodasPorDocente(id);

        // 2. Desvincular grados
        List<Grado> grados = gradoRepository.findByProfesorId(id);
        for (Grado grado : grados) {
            grado.setProfesor(null);
            gradoRepository.save(grado);
        }

        // 3. Eliminar asignaturas asociadas
        List<Asignatura> asignaturas = asignaturaRepository.findByProfesorId(id);
        asignaturaRepository.deleteAll(asignaturas);

        // 4. Eliminar docente
        usuarioRepository.delete(usuario);
    }
}
