package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.GradoDTO;
import com.jorgestor.backend.model.Grado;
import com.jorgestor.backend.model.Asignatura;
import com.jorgestor.backend.repository.GradoRepository;
import com.jorgestor.backend.repository.AsignaturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradoService {

    private final GradoRepository gradoRepository;
    private final AsignaturaRepository asignaturaRepository;

    public GradoService(GradoRepository gradoRepository, AsignaturaRepository asignaturaRepository) {
        this.gradoRepository = gradoRepository;
        this.asignaturaRepository = asignaturaRepository;
    }

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(GradoService.class);

    public List<GradoDTO> listarGrados(Long docenteId) {
        logger.info("DEBUG - Buscando grados para docenteId: {}", docenteId);
        
        // Obtenemos asignaturas del profesor
        List<Asignatura> asignaturas = asignaturaRepository.findByProfesorId(docenteId);
        logger.info("DEBUG - Asignaturas encontradas: {}", asignaturas.size());
        
        // Extraemos grados únicos de esas asignaturas
        List<Grado> grados = asignaturas.stream()
                .flatMap(a -> a.getGrados().stream())
                .distinct()
                .collect(Collectors.toList());
        
        logger.info("DEBUG - Grados únicos encontrados: {}", grados.size());
        
        return grados.stream()
                .map(g -> new GradoDTO(g.getId(), g.getCodigo(), g.getTitulo()))
                .collect(Collectors.toList());
    }

    public GradoDTO obtenerGrado(Long id) {
        Grado g = gradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));
        return new GradoDTO(g.getId(), g.getCodigo(), g.getTitulo());
    }

    public GradoDTO crearGrado(GradoDTO dto) {
        logger.info("DEBUG - Intentando crear grado: {}", dto.getCodigo());
        if (gradoRepository.findByCodigo(dto.getCodigo()).isPresent()) {
            logger.warn("DEBUG - El grado {} ya existe", dto.getCodigo());
            throw new RuntimeException("El código de grado ya existe");
        }
        Grado grado = new Grado(dto.getCodigo(), dto.getTitulo());
        Grado guardado = gradoRepository.save(grado);
        logger.info("DEBUG - Grado guardado exitosamente con ID: {}", guardado.getId());
        return new GradoDTO(guardado.getId(), guardado.getCodigo(), guardado.getTitulo());
    }

    public GradoDTO actualizarGrado(Long id, GradoDTO dto) {
        Grado grado = gradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));
        
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
