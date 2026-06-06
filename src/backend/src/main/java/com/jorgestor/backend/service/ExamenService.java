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
        
        int numFacil = (int) Math.round(totalPreguntas * (config.getProporcionFacil() / 100.0));
        int numMedia = (int) Math.round(totalPreguntas * (config.getProporcionMedia() / 100.0));
        int numDificil = totalPreguntas - numFacil - numMedia;

        seleccion.addAll(obtenerAleatorias(banco.getOrDefault(DificultadPregunta.FACIL, new ArrayList<>()), numFacil));
        seleccion.addAll(obtenerAleatorias(banco.getOrDefault(DificultadPregunta.MEDIO, new ArrayList<>()), numMedia));
        seleccion.addAll(obtenerAleatorias(banco.getOrDefault(DificultadPregunta.DIFICIL, new ArrayList<>()), numDificil));

        if (seleccion.size() < totalPreguntas) {
            throw new RuntimeException("No hay suficientes preguntas para cumplir con la proporción solicitada");
        }

        Collections.shuffle(seleccion);
        return seleccion;
    }

    private List<PreguntaDTO> obtenerAleatorias(List<PreguntaDTO> lista, int cantidad) {
        if (lista.size() < cantidad) {
            throw new RuntimeException("No hay suficientes preguntas de dificultad específica");
        }
        List<PreguntaDTO> copia = new ArrayList<>(lista);
        Collections.shuffle(copia);
        return copia.subList(0, cantidad);
    }

    private String generarClaveAleatoria() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
