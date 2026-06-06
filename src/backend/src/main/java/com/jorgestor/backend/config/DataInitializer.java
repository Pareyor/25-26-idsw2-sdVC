package com.jorgestor.backend.config;

import com.jorgestor.backend.model.*;
import com.jorgestor.backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

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
        Grado infoGrado = null;
        if (gradoRepository.count() == 0) {
            infoGrado = gradoRepository.save(new Grado("GII", "Grado en Ingeniería Informática"));
            gradoRepository.save(new Grado("GIM", "Grado en Ingeniería Mecánica"));
            System.out.println("Grados de prueba creados.");
        } else {
            infoGrado = gradoRepository.findByCodigo("GII").orElse(null);
        }

        if (asignaturaRepository.count() == 0 && infoGrado != null) {
            asignaturaRepository.save(new Asignatura("IS1", "Ingeniería de Software I", "2025-2026", infoGrado));
            asignaturaRepository.save(new Asignatura("IS2", "Ingeniería de Software II", "2025-2026", infoGrado));
            asignaturaRepository.save(new Asignatura("SI", "Sistemas Inteligentes", "2025-2026", infoGrado));
            System.out.println("Asignaturas de prueba creadas.");
        }

        if (alumnoRepository.count() == 0 && infoGrado != null) {
            alumnoRepository.save(new Alumno("100456789", "Juan", "Pérez García", infoGrado));
            alumnoRepository.save(new Alumno("100456790", "María", "López Rodríguez", infoGrado));
            alumnoRepository.save(new Alumno("100456791", "Carlos", "Sánchez Martínez", infoGrado));
            System.out.println("Alumnos de prueba creados.");
        }

        if (preguntaRepository.count() < 100) {
            List<Asignatura> asignaturas = asignaturaRepository.findAll();
            DificultadPregunta[] dificultades = DificultadPregunta.values();
            Tema[] temas = Tema.values();

            for (Asignatura asignatura : asignaturas) {
                for (int i = 0; i < 100; i++) {
                    DificultadPregunta dif = dificultades[i % dificultades.length];
                    Tema tema = temas[i % temas.length];

                    Pregunta p = new Pregunta("Pregunta " + (i + 1) + " de " + asignatura.getTitulo() + " [" + tema + " - " + dif + "]", tema, dif, asignatura);
                    p.getRespuestas().add(new Respuesta("Respuesta correcta", true, p));
                    p.getRespuestas().add(new Respuesta("Respuesta falsa", false, p));
                    preguntaRepository.save(p);
                }
            }
            System.out.println("Batería de preguntas ampliada a 100 por asignatura.");
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
