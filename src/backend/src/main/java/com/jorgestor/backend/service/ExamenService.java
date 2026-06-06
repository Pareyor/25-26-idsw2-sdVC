package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.*;
import com.jorgestor.backend.model.*;
import com.jorgestor.backend.repository.ExamenRepository;
import com.jorgestor.backend.repository.AlumnoRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExamenService {

    private final AsignaturaService asignaturaService;
    private final PreguntaService preguntaService;
    private final ExamenSessionService sessionService;
    private final ExamenRepository examenRepository;
    private final AlumnoRepository alumnoRepository;

    public ExamenService(AsignaturaService asignaturaService, PreguntaService preguntaService, ExamenSessionService sessionService, ExamenRepository examenRepository, AlumnoRepository alumnoRepository) {
        this.asignaturaService = asignaturaService;
        this.preguntaService = preguntaService;
        this.sessionService = sessionService;
        this.examenRepository = examenRepository;
        this.alumnoRepository = alumnoRepository;
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
            
            for (int i = 0; i < config.getNumExamenes(); i++) {
                List<PreguntaDTO> seleccionadas = seleccionarPreguntas(
                        config, 
                        config.getNumPreguntas(), 
                        bancoPorDificultad
                );
                
                PlantillaExamenDTO plantilla = new PlantillaExamenDTO();
                plantilla.setGradoId(config.getGradoId());
                plantilla.setAsignaturaId(asignatura.getId());
                plantilla.setTipoExamen(dto.getTipoExamen());
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

        // Validar stock estricto
        validarStock(banco.getOrDefault(DificultadPregunta.FACIL, new ArrayList<>()), numFacil, "Fácil");
        validarStock(banco.getOrDefault(DificultadPregunta.MEDIO, new ArrayList<>()), numMedia, "Media");
        validarStock(banco.getOrDefault(DificultadPregunta.DIFICIL, new ArrayList<>()), numDificil, "Difícil");

        // Seleccionar
        seleccion.addAll(obtenerDisponibles(banco.getOrDefault(DificultadPregunta.FACIL, new ArrayList<>()), numFacil));
        seleccion.addAll(obtenerDisponibles(banco.getOrDefault(DificultadPregunta.MEDIO, new ArrayList<>()), numMedia));
        seleccion.addAll(obtenerDisponibles(banco.getOrDefault(DificultadPregunta.DIFICIL, new ArrayList<>()), numDificil));

        Collections.shuffle(seleccion);
        return seleccion;
    }

    private void validarStock(List<PreguntaDTO> banco, int solicitado, String dificultad) {
        if (banco.size() < solicitado) {
            throw new RuntimeException("No hay suficientes preguntas de dificultad " + dificultad + ". Solicitadas: " + solicitado + ", Disponibles: " + banco.size());
        }
    }

    private List<PreguntaDTO> obtenerDisponibles(List<PreguntaDTO> lista, int cantidad) {
        if (cantidad <= 0) return new ArrayList<>();
        List<PreguntaDTO> copia = new ArrayList<>(lista);
        Collections.shuffle(copia);
        return copia.subList(0, Math.min(copia.size(), cantidad));
    }

    // ... métodos anteriores

    public void persistirAsignaciones(List<PlantillaExamenDTO> plantillas, List<Long> alumnoIds) {
        // 1. Agrupar plantillas por grado
        Map<Long, List<PlantillaExamenDTO>> plantillasPorGrado = plantillas.stream()
                .collect(Collectors.groupingBy(PlantillaExamenDTO::getGradoId));

        // 2. Procesar cada grado
        for (Map.Entry<Long, List<PlantillaExamenDTO>> entry : plantillasPorGrado.entrySet()) {
            Long gradoId = entry.getKey();
            List<PlantillaExamenDTO> plantillasGrado = entry.getValue();
            
            // 3. Filtrar alumnos seleccionados que pertenecen a este grado
            List<Alumno> alumnosGrado = alumnoRepository.findByGradoId(gradoId).stream()
                    .filter(a -> alumnoIds.contains(a.getId()))
                    .collect(Collectors.toList());

            // 4. Distribuir plantillas entre alumnos (forma simple: round-robin)
            int indexPlantilla = 0;
            for (Alumno alumno : alumnosGrado) {
                PlantillaExamenDTO plantilla = plantillasGrado.get(indexPlantilla % plantillasGrado.size());
                Asignatura asignatura = asignaturaService.findEntityById(plantilla.getAsignaturaId());
                
                Examen examen = new Examen(alumno, asignatura, plantilla.getTipoExamen(), plantilla.getClave());
                examenRepository.save(examen);
                
                indexPlantilla++;
            }
        }
    }

    private String generarClaveAleatoria() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
