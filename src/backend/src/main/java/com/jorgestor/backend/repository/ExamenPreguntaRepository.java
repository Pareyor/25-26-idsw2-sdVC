package com.jorgestor.backend.repository;

import com.jorgestor.backend.model.ExamenPregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenPreguntaRepository extends JpaRepository<ExamenPregunta, Long> {
    List<ExamenPregunta> findByExamenId(Long examenId);
}
