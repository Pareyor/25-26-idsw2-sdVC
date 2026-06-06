package com.jorgestor.backend.config;

import com.jorgestor.backend.model.*;
import com.jorgestor.backend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final GradoRepository gradoRepository;
    private final AsignaturaRepository asignaturaRepository;
    private final AlumnoRepository alumnoRepository;
    private final PreguntaRepository preguntaRepository;
    private final PasswordEncoder passwordEncoder;

    private final String[] NOMBRES = {"Juan", "María", "Carlos", "Ana", "Luis", "Elena", "Javier", "Lucía", "Diego", "Sofía"};
    private final String[] APELLIDOS = {"García", "Rodríguez", "Martínez", "López", "Pérez", "Sánchez", "Gómez", "Jiménez", "Ruiz", "Hernández"};

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
            Grado g1 = gradoRepository.save(new Grado("GII", "Grado en Ingeniería Informática"));
            Grado g2 = gradoRepository.save(new Grado("GIM", "Grado en Ingeniería Mecánica"));
            List<Grado> todosLosGrados = List.of(g1, g2);
            Random random = new Random();
            
            Map<String, String[]> temasPorAsignatura = Map.of(
                "Programación", new String[]{"Variables", "Bucles", "POO", "Excepciones"},
                "Software", new String[]{"Requisitos", "Diseño", "Arquitectura", "Pruebas"},
                "Bases de Datos", new String[]{"SQL", "Modelado", "Normalización", "NoSQL"}
            );

            for (Map.Entry<String, String[]> entry : temasPorAsignatura.entrySet()) {
                Asignatura asig = asignaturaRepository.save(new Asignatura("ASIG-" + entry.getKey().substring(0,3).toUpperCase(), entry.getKey(), "2025-2026", todosLosGrados));
                
                for (Grado g : todosLosGrados) {
                    for (int j = 1; j <= 5; j++) {
                        String nombre = NOMBRES[random.nextInt(NOMBRES.length)];
                        String apellido = APELLIDOS[random.nextInt(APELLIDOS.length)] + " " + APELLIDOS[random.nextInt(APELLIDOS.length)];
                        String dni = String.format("%08d%c", random.nextInt(100000000), (char)('A' + random.nextInt(26)));
                        alumnoRepository.save(new Alumno(dni, nombre, apellido, g, "25/26"));
                    }
                }

                String[] temas = entry.getValue();
                for (int k = 0; k < 150; k++) {
                    String tema = temas[k % temas.length];
                    Pregunta p = new Pregunta("Pregunta " + tema + " " + (k + 1), TipoPregunta.TEORIA, tema, DificultadPregunta.values()[k % 3], asig);
                    p.getRespuestas().add(new Respuesta("Correcta", true, p));
                    p.getRespuestas().add(new Respuesta("Falsa", false, p));
                    preguntaRepository.save(p);
                }
            }
            System.out.println("Base de datos poblada con alumnos reales y datos distribuidos.");
        }
        
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Usuario("admin", passwordEncoder.encode("admin123"), "admin@jorgestor.com", "Admin", "Institucional", Role.ROLE_ADMIN));
            usuarioRepository.save(new Usuario("docente", passwordEncoder.encode("docente123"), "docente@jorgestor.com", "Docente", "Ejemplo", Role.ROLE_DOCENTE));
        }
    }
}
