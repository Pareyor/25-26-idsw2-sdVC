package com.jorgestor.backend.config;

import com.jorgestor.backend.model.Role;
import com.jorgestor.backend.model.Usuario;
import com.jorgestor.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            // Crear Administrador (Sin builder, usando constructor manual)
            Usuario admin = new Usuario(
                "admin",
                passwordEncoder.encode("admin123"),
                "admin@jorgestor.com",
                "Admin",
                "Institucional",
                Role.ROLE_ADMIN
            );
            usuarioRepository.save(admin);

            // Crear Docente
            Usuario docente = new Usuario(
                "docente",
                passwordEncoder.encode("docente123"),
                "docente@jorgestor.com",
                "Docente",
                "Ejemplo",
                Role.ROLE_DOCENTE
            );
            usuarioRepository.save(docente);
            
            System.out.println("Usuarios de prueba creados: admin/admin123 y docente/docente123");
        }
    }
}
