package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.JwtResponse;
import com.jorgestor.backend.dto.LoginRequest;
import com.jorgestor.backend.model.Usuario;
import com.jorgestor.backend.repository.UsuarioRepository;
import com.jorgestor.backend.security.JwtUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    // Constructor manual
    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public JwtResponse login(LoginRequest loginRequest) {
        Usuario usuario = usuarioRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            throw new BadCredentialsException("Contraseña incorrecta");
        }

        String token = jwtUtils.generateJwtToken(usuario.getUsername(), usuario.getRole().name());
        return new JwtResponse(token, usuario.getUsername(), usuario.getRole().name());
    }

    public void invalidateSession(String token) {
        // En una arquitectura JWT pura, el servidor es stateless.
        // Aquí podríamos añadir el token a una "Blacklist" si fuera necesario.
        // Por ahora, simplemente registramos el evento o validamos el token.
        System.out.println("Sesión finalizada para el token: " + token.substring(0, 10) + "...");
    }
}
