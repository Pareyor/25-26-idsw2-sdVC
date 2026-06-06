package com.jorgestor.backend.repository;

import com.jorgestor.backend.model.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
    List<Pregunta> findByAsignaturaIdAndTemaIn(Long asignaturaId, List<String> temas);
    
    @org.springframework.data.jpa.repository.Query("SELECT DISTINCT p.tema FROM Pregunta p WHERE p.asignatura.id = :asignaturaId")
    List<String> findDistinctTemasByAsignaturaId(Long asignaturaId);
}
