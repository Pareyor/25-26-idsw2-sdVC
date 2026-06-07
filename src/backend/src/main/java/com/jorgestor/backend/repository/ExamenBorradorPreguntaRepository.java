package com.jorgestor.backend.repository;

import com.jorgestor.backend.model.ExamenBorradorPregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenBorradorPreguntaRepository extends JpaRepository<ExamenBorradorPregunta, Long> {
    List<ExamenBorradorPregunta> findByExamenBorradorId(Long examenBorradorId);
}
