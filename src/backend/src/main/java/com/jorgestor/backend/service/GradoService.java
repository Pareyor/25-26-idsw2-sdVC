package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.GradoDTO;
import com.jorgestor.backend.model.Asignatura;
import com.jorgestor.backend.model.Grado;
import com.jorgestor.backend.repository.AsignaturaRepository;
import com.jorgestor.backend.repository.GradoRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradoService {

    private final GradoRepository gradoRepository;
    private final AsignaturaRepository asignaturaRepository;
    private static final Logger logger = LoggerFactory.getLogger(GradoService.class);

    public GradoService(GradoRepository gradoRepository, AsignaturaRepository asignaturaRepository) {
        this.gradoRepository = gradoRepository;
        this.asignaturaRepository = asignaturaRepository;
    }

    public List<GradoDTO> listarGrados(Long docenteId) {
        logger.info("DEBUG - Buscando grados para docenteId: {}", docenteId);

        // Obtenemos grados que pertenecen directamente al profesor
        List<Grado> gradosDirectos = gradoRepository.findByProfesorId(docenteId);
        
        // Obtenemos grados vinculados a sus asignaturas
        List<Grado> gradosPorAsignatura = gradoRepository.findByAsignaturasProfesorId(docenteId);

        // Combinamos y eliminamos duplicados
        java.util.Set<Grado> todos = new java.util.HashSet<>(gradosDirectos);
        todos.addAll(gradosPorAsignatura);

        logger.info("DEBUG - Grados totales encontrados: {}", todos.size());

        return todos.stream()
                .map(g -> new GradoDTO(g.getId(), g.getCodigo(), g.getTitulo()))
                .collect(Collectors.toList());
    }

    public GradoDTO obtenerGrado(Long id) {
        Grado g = gradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));
        return new GradoDTO(g.getId(), g.getCodigo(), g.getTitulo());
    }

    public GradoDTO crearGrado(GradoDTO dto, Long docenteId) {
        logger.info("DEBUG - Intentando crear grado: {} para docente: {}", dto.getCodigo(), docenteId);
        if (gradoRepository.findByCodigoAndProfesorId(dto.getCodigo(), docenteId).isPresent()) {
            logger.warn("DEBUG - El grado {} ya existe para este docente", dto.getCodigo());
            throw new RuntimeException("El código de grado ya existe para usted");
        }
        Grado grado = new Grado(dto.getCodigo(), dto.getTitulo());
        
        com.jorgestor.backend.model.Usuario profesor = new com.jorgestor.backend.model.Usuario();
        profesor.setId(docenteId);
        grado.setProfesor(profesor);

        Grado guardado = gradoRepository.save(grado);
        logger.info("DEBUG - Grado guardado exitosamente con ID: {}", guardado.getId());
        return new GradoDTO(guardado.getId(), guardado.getCodigo(), guardado.getTitulo());
    }

    public GradoDTO actualizarGrado(Long id, GradoDTO dto) {
        Grado grado = gradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));
        
        // Si el código cambia, verificar que el nuevo no lo tenga ya este profesor
        if (!grado.getCodigo().equals(dto.getCodigo())) {
            if (gradoRepository.findByCodigoAndProfesorId(dto.getCodigo(), grado.getProfesor().getId()).isPresent()) {
                throw new RuntimeException("El código de grado ya existe para usted");
            }
        }

        grado.setCodigo(dto.getCodigo());
        grado.setTitulo(dto.getTitulo());
        
        Grado guardado = gradoRepository.save(grado);
        return new GradoDTO(guardado.getId(), guardado.getCodigo(), guardado.getTitulo());
    }

    public void eliminarGrado(Long id) {
        if (!gradoRepository.existsById(id)) {
            throw new RuntimeException("Grado no encontrado");
        }
        gradoRepository.deleteById(id);
    }

    public Grado findEntityById(Long id) {
        return gradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));
    }
}
