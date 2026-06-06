package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.*;
import com.jorgestor.backend.model.Asignatura;
import com.jorgestor.backend.model.DificultadPregunta;
import com.jorgestor.backend.model.Tema;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExamenService {

    private final AsignaturaService asignaturaService;
    private final PreguntaService preguntaService;
    private final ExamenSessionService sessionService;

    public ExamenService(AsignaturaService asignaturaService, PreguntaService preguntaService, ExamenSessionService sessionService) {
        this.asignaturaService = asignaturaService;
        this.preguntaService = preguntaService;
        this.sessionService = sessionService;
    }

    public GeneracionExitoDTO generarExamenes(GenerarExamenesDTO dto, Long docenteId) {
        // 1. Validar asignatura y docente
        Asignatura asignatura = asignaturaService.findEntityById(dto.getAsignaturaId());
        if (asignatura.getProfesor() != null && !asignatura.getProfesor().getId().equals(docenteId)) {
            throw new RuntimeException("No tiene permisos sobre esta asignatura");
        }

        // 2. Obtener banco de preguntas
        List<Tema> temas = dto.getTemas();
        
        List<PreguntaDTO> banco = preguntaService.obtenerBancoPreguntas(asignatura.getId(), temas);

        System.out.println(">>> Banco de preguntas recuperado: " + banco.size() + " preguntas");

        // 3. Agrupar banco por dificultad
        Map<DificultadPregunta, List<PreguntaDTO>> bancoPorDificultad = banco.stream()
                .collect(Collectors.groupingBy(PreguntaDTO::getDificultad));

        List<PlantillaExamenDTO> todasLasPlantillas = new ArrayList<>();
        Map<Long, Integer> resumen = new HashMap<>();

        // 4. Generar por cada grado configurado
        for (ConfigGradoDTO config : dto.getConfiguracionesGrado()) {
            List<PlantillaExamenDTO> plantillasGrado = new ArrayList<>();
            
            for (int i = 0; i < config.getNumTipos(); i++) {
                List<PreguntaDTO> seleccionadas = seleccionarPreguntas(
                        config, 
                        dto.getNumPreguntas(), 
                        bancoPorDificultad
                );
                
                PlantillaExamenDTO plantilla = new PlantillaExamenDTO();
                plantilla.setGradoId(config.getGradoId());
                plantilla.setAsignaturaId(asignatura.getId());
                plantilla.setEvaluacion(dto.getEvaluacion());
                plantilla.setPreguntas(seleccionadas);
                plantilla.setClave(generarClaveAleatoria());
                
                plantillasGrado.add(plantilla);
            }
            
            todasLasPlantillas.addAll(plantillasGrado);
            resumen.put(config.getGradoId(), plantillasGrado.size());
        }

        // 5. Guardar en sesión
        sessionService.guardarBorradores(todasLasPlantillas);

        return new GeneracionExitoDTO(todasLasPlantillas.size(), resumen);
    }

    private List<PreguntaDTO> seleccionarPreguntas(ConfigGradoDTO config, Integer totalPreguntas, Map<DificultadPregunta, List<PreguntaDTO>> banco) {
        List<PreguntaDTO> seleccion = new ArrayList<>();
        
        int facil = config.getProporcionFacil() != null ? config.getProporcionFacil() : 0;
        int media = config.getProporcionMedia() != null ? config.getProporcionMedia() : 0;
        int dificil = config.getProporcionDificil() != null ? config.getProporcionDificil() : 0;
        
        // Normalizar si la suma no es 100
        int suma = facil + media + dificil;
        if (suma == 0) { facil = 33; media = 33; dificil = 34; }
        else { facil = (facil * 100) / suma; media = (media * 100) / suma; dificil = 100 - facil - media; }

        int numFacil = (int) Math.round(totalPreguntas * (facil / 100.0));
        int numMedia = (int) Math.round(totalPreguntas * (media / 100.0));
        int numDificil = totalPreguntas - numFacil - numMedia;

        // Intentamos obtener preguntas por dificultad
        seleccion.addAll(obtenerDisponibles(banco.getOrDefault(DificultadPregunta.FACIL, new ArrayList<>()), numFacil));
        seleccion.addAll(obtenerDisponibles(banco.getOrDefault(DificultadPregunta.MEDIO, new ArrayList<>()), numMedia));
        seleccion.addAll(obtenerDisponibles(banco.getOrDefault(DificultadPregunta.DIFICIL, new ArrayList<>()), numDificil));

        // Si faltan preguntas por falta de stock en alguna dificultad, rellenamos con lo que haya
        if (seleccion.size() < totalPreguntas) {
            List<PreguntaDTO> todasRestantes = banco.values().stream()
                    .flatMap(List::stream)
                    .filter(p -> !seleccion.contains(p))
                    .collect(Collectors.toList());
            
            int faltantes = totalPreguntas - seleccion.size();
            seleccion.addAll(obtenerDisponibles(todasRestantes, faltantes));
        }

        Collections.shuffle(seleccion);
        return seleccion;
    }

    private List<PreguntaDTO> obtenerDisponibles(List<PreguntaDTO> lista, int cantidad) {
        if (cantidad <= 0) return new ArrayList<>();
        List<PreguntaDTO> copia = new ArrayList<>(lista);
        Collections.shuffle(copia);
        return copia.subList(0, Math.min(copia.size(), cantidad));
    }

    // ... métodos anteriores

    public void persistirAsignaciones(List<PlantillaExamenDTO> plantillas, List<AlumnoDTO> alumnos) {
        // Lógica de persistencia: 
        // 1. Iterar sobre las plantillas.
        // 2. Asociar plantillas a alumnos según el grado.
        // 3. Guardar en repositorio.
        // Nota: Implementación simplificada para el ejemplo.
        for (PlantillaExamenDTO plantilla : plantillas) {
            List<AlumnoDTO> alumnosGrado = alumnos.stream()
                .filter(a -> a.getGradoId().equals(plantilla.getGradoId()))
                .collect(Collectors.toList());
            
            for (AlumnoDTO alumno : alumnosGrado) {
                // Crear entidad Examen e insertar en base de datos
                // examenRepository.save(new Examen(plantilla, alumno));
                System.out.println("Persistiendo examen para alumno: " + alumno.getDni());
            }
        }
    }

    private String generarClaveAleatoria() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
