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
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Usuario("admin", passwordEncoder.encode("admin123"), "admin@jorgestor.com", "Admin", "Institucional", Role.ROLE_ADMIN));
            usuarioRepository.save(new Usuario("docente", passwordEncoder.encode("docente123"), "docente@jorgestor.com", "Docente", "Ejemplo", Role.ROLE_DOCENTE));
            usuarioRepository.save(new Usuario("72224668E", passwordEncoder.encode("pablo123"), "pablo.rey@ejemplo.com", "Pablo", "Rey Ortiz", Role.ROLE_DOCENTE));
        }

        Usuario docente = usuarioRepository.findByUsername("docente").orElseThrow();
        // Usuario pablo = usuarioRepository.findByUsername("72224668E").orElseThrow(); // Pablo no recibirá datos automáticos
        List<Usuario> docentes = List.of(docente); // Solo el usuario "docente"

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

            // Datos reales para las asignaturas
            Map<String, List<Object[]>> preguntasReales = Map.of(
                "Programación", List.of(
                    new Object[]{"¿Qué es una variable en programación?", "Variables", DificultadPregunta.FACIL, "Un espacio en memoria para almacenar un dato", "Un error del sistema"},
                    new Object[]{"¿Para qué sirve un bucle 'for'?", "Bucles", DificultadPregunta.FACIL, "Para repetir un bloque de código un número determinado de veces", "Para saltar líneas de código"},
                    new Object[]{"¿Qué es el polimorfismo en POO?", "POO", DificultadPregunta.DIFICIL, "La capacidad de un objeto de tomar varias formas", "Un tipo de variable numérica"},
                    new Object[]{"¿Qué palabra reservada se usa para capturar una excepción?", "Excepciones", DificultadPregunta.MEDIO, "catch", "get"},
                    new Object[]{"¿Cuál es la función del operador '&&'?", "Variables", DificultadPregunta.MEDIO, "Operador lógico AND", "Operador de suma"},
                    new Object[]{"¿Qué es una clase abstracta?", "POO", DificultadPregunta.DIFICIL, "Una clase que no se puede instanciar directamente", "Una clase sin métodos"},
                    new Object[]{"¿Qué hace el comando 'break'?", "Bucles", DificultadPregunta.MEDIO, "Sale inmediatamente de un bucle", "Pausa el programa"},
                    new Object[]{"¿Qué es un constructor?", "POO", DificultadPregunta.MEDIO, "Un método especial para inicializar objetos", "Un tipo de dato"},
                    new Object[]{"¿Qué es la recursividad?", "Bucles", DificultadPregunta.DIFICIL, "Una función que se llama a sí misma", "Un bucle infinito"},
                    new Object[]{"¿Qué es un puntero?", "Variables", DificultadPregunta.DIFICIL, "Una variable que almacena una dirección de memoria", "Una flecha en el código"}
                ),
                "Software", List.of(
                    new Object[]{"¿Qué es un requisito no funcional?", "Requisitos", DificultadPregunta.MEDIO, "Una restricción sobre los servicios o funciones del sistema", "Una característica que el usuario no quiere"},
                    new Object[]{"¿Qué es el patrón Singleton?", "Diseño", DificultadPregunta.DIFICIL, "Garantiza que una clase tenga una única instancia", "Un patrón para crear muchas listas"},
                    new Object[]{"¿Cuál es el objetivo de las pruebas unitarias?", "Pruebas", DificultadPregunta.FACIL, "Verificar que un componente individual funcione correctamente", "Probar todo el sistema a la vez"},
                    new Object[]{"¿Qué significa 'escalabilidad' en arquitectura?", "Arquitectura", DificultadPregunta.MEDIO, "Capacidad del sistema para manejar un crecimiento en la carga", "Velocidad de internet"},
                    new Object[]{"¿Qué es un diagrama de clases?", "Diseño", DificultadPregunta.FACIL, "Una representación de la estructura estática del sistema", "Un dibujo de la pantalla"},
                    new Object[]{"¿Qué es la metodología Scrum?", "Requisitos", DificultadPregunta.FACIL, "Un marco de trabajo ágil para la gestión de proyectos", "Un lenguaje de programación"},
                    new Object[]{"¿Qué es el 'acoplamiento' en software?", "Diseño", DificultadPregunta.DIFICIL, "El grado de interdependencia entre módulos", "La unión de cables"},
                    new Object[]{"¿Qué es la 'cohesión'?", "Diseño", DificultadPregunta.DIFICIL, "El grado en que las tareas de un módulo están relacionadas", "La velocidad de ejecución"},
                    new Object[]{"¿Para qué sirve un Mock?", "Pruebas", DificultadPregunta.MEDIO, "Simular el comportamiento de un objeto real", "Para decorar el código"},
                    new Object[]{"¿Qué es un microservicio?", "Arquitectura", DificultadPregunta.DIFICIL, "Un servicio pequeño e independiente en una arquitectura distribuida", "Un programa que ocupa pocos KB"}
                ),
                "Bases de Datos", List.of(
                    new Object[]{"¿Qué significa la sigla SQL?", "SQL", DificultadPregunta.FACIL, "Structured Query Language", "Simple Quality List"},
                    new Object[]{"¿Qué es una clave primaria?", "Modelado", DificultadPregunta.FACIL, "Un campo que identifica de forma única cada registro", "La contraseña de la base de datos"},
                    new Object[]{"¿Qué es la normalización?", "Normalización", DificultadPregunta.DIFICIL, "Proceso para organizar los datos y evitar redundancia", "Hacer que todos los datos sean iguales"},
                    new Object[]{"¿Cuál es la diferencia entre INNER JOIN y LEFT JOIN?", "SQL", DificultadPregunta.MEDIO, "INNER devuelve coincidencias, LEFT devuelve todo de la izquierda", "No hay diferencia"},
                    new Object[]{"¿Qué es una base de datos NoSQL?", "NoSQL", DificultadPregunta.MEDIO, "Una base de datos que no usa el modelo relacional tradicional", "Una base de datos que no usa SQL para nada"},
                    new Object[]{"¿Qué es una transacción (ACID)?", "SQL", DificultadPregunta.DIFICIL, "Una unidad de trabajo que se ejecuta completamente o no se ejecuta", "Un pago con tarjeta"},
                    new Object[]{"¿Qué es un índice?", "Modelado", DificultadPregunta.MEDIO, "Una estructura que mejora la velocidad de las consultas", "El número de página de la tabla"},
                    new Object[]{"¿Qué es una clave foránea?", "Modelado", DificultadPregunta.MEDIO, "Un campo que referencia la clave primaria de otra tabla", "Una clave de otro país"},
                    new Object[]{"¿Para qué sirve la cláusula GROUP BY?", "SQL", DificultadPregunta.MEDIO, "Para agrupar filas que tienen los mismos valores", "Para ordenar la lista"},
                    new Object[]{"¿Qué es el Teorema CAP?", "NoSQL", DificultadPregunta.DIFICIL, "Establece que es imposible garantizar Consistencia, Disponibilidad y Tolerancia al particionamiento a la vez", "Un teorema de geometría"}
                )
            );

            for (Usuario d : docentes) {
                for (Map.Entry<String, List<Object[]>> entry : preguntasReales.entrySet()) {
                    String nombreAsig = entry.getKey();
                    Asignatura asig = new Asignatura(nombreAsig.substring(0,3).toUpperCase(), nombreAsig, "2025-2026", todosLosGrados);
                    asig.setProfesor(d);
                    asignaturaRepository.save(asig);
                    
                    for (Grado g : todosLosGrados) {
                        for (int j = 1; j <= 5; j++) {
                            String nombre = NOMBRES[random.nextInt(NOMBRES.length)];
                            String apellido = APELLIDOS[random.nextInt(APELLIDOS.length)] + " " + APELLIDOS[random.nextInt(APELLIDOS.length)];
                            String dni = String.format("%08d%c", random.nextInt(100000000), (char)('A' + random.nextInt(26)));
                            alumnoRepository.save(new Alumno(dni, nombre, apellido, g, "25/26"));
                        }
                    }

                    for (Object[] pData : entry.getValue()) {
                        Pregunta p = new Pregunta((String)pData[0], TipoPregunta.TEORIA, (String)pData[1], (DificultadPregunta)pData[2], asig);
                        p.getRespuestas().add(new Respuesta((String)pData[3], true, p));
                        p.getRespuestas().add(new Respuesta((String)pData[4], false, p));
                        preguntaRepository.save(p);
                    }
                }
            }
            System.out.println("Base de datos poblada con preguntas reales, alumnos y datos distribuidos para ambos docentes.");
        }
    }
}
