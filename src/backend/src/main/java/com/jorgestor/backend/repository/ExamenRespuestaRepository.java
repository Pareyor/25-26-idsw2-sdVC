package com.jorgestor.backend.repository;

import com.jorgestor.backend.model.ExamenRespuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenRespuestaRepository extends JpaRepository<ExamenRespuesta, Long> {
    List<ExamenRespuesta> findByExamenId(Long examenId);
}
