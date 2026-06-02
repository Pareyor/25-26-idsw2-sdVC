package com.jorgestor.backend.config;

import com.jorgestor.backend.model.*;
import com.jorgestor.backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final GradoRepository gradoRepository;
    private final AsignaturaRepository asignaturaRepository;
    private final AlumnoRepository alumnoRepository;
    private final PreguntaRepository preguntaRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, 
                           GradoRepository gradoRepository, 
                           AsignaturaRepository asignaturaRepository,
                           AlumnoRepository alumnoRepository,
                           PreguntaRepository preguntaRepository,
                           PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.gradoRepository = gradoRepository;
        this.asignaturaRepository = asignaturaRepository;
        this.alumnoRepository = alumnoRepository;
        this.preguntaRepository = preguntaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (gradoRepository.count() == 0) {
            gradoRepository.save(new Grado("GII", "Grado en Ingeniería Informática"));
            gradoRepository.save(new Grado("GIM", "Grado en Ingeniería Mecánica"));
            System.out.println("Grados de prueba creados.");
        }

        if (asignaturaRepository.count() == 0) {
            asignaturaRepository.save(new Asignatura("IS1", "Ingeniería de Software I", "2025-2026"));
            asignaturaRepository.save(new Asignatura("IS2", "Ingeniería de Software II", "2025-2026"));
            asignaturaRepository.save(new Asignatura("SI", "Sistemas Inteligentes", "2025-2026"));
            System.out.println("Asignaturas de prueba creadas.");
        }

        if (alumnoRepository.count() == 0) {
            alumnoRepository.save(new Alumno("100456789", "Juan", "Pérez García"));
            alumnoRepository.save(new Alumno("100456790", "María", "López Rodríguez"));
            alumnoRepository.save(new Alumno("100456791", "Carlos", "Sánchez Martínez"));
            System.out.println("Alumnos de prueba creados.");
        }

        if (preguntaRepository.count() == 0) {
            preguntaRepository.save(new Pregunta("¿Qué es el patrón MVC?", Tema.TEORIA, DificultadPregunta.MEDIO));
            preguntaRepository.save(new Pregunta("Explique la diferencia entre agregación y composición.", Tema.TEORIA, DificultadPregunta.DIFICIL));
            preguntaRepository.save(new Pregunta("¿Para qué sirve un diagrama de clases?", Tema.DISENO, DificultadPregunta.FACIL));
            System.out.println("Preguntas de prueba creadas.");
        }

        if (usuarioRepository.count() == 0) {
            // Crear Administrador
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
