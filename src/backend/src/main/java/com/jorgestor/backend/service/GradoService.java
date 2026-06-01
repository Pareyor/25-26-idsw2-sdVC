package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.GradoDTO;
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
}
