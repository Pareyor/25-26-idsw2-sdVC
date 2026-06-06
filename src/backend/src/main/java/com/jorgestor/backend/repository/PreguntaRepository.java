package com.jorgestor.backend.repository;

import com.jorgestor.backend.model.Pregunta;
import com.jorgestor.backend.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
    List<Pregunta> findByAsignaturaIdAndTemaIn(Long asignaturaId, List<Tema> temas);
}
