package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.GradoDTO;
import com.jorgestor.backend.model.Grado;
import com.jorgestor.backend.repository.GradoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradoService {

    private final GradoRepository gradoRepository;

    public GradoService(GradoRepository gradoRepository) {
        this.gradoRepository = gradoRepository;
    }

    public List<GradoDTO> listarGrados() {
        return gradoRepository.findAll().stream()
                .map(g -> new GradoDTO(g.getId(), g.getCodigo(), g.getTitulo()))
                .collect(Collectors.toList());
    }

    public GradoDTO obtenerGrado(Long id) {
        Grado g = gradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));
        return new GradoDTO(g.getId(), g.getCodigo(), g.getTitulo());
    }

    public GradoDTO crearGrado(GradoDTO dto) {
        if (gradoRepository.findByCodigo(dto.getCodigo()).isPresent()) {
            throw new RuntimeException("El código de grado ya existe");
        }
        Grado grado = new Grado(dto.getCodigo(), dto.getTitulo());
        Grado guardado = gradoRepository.save(grado);
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
}
