package com.jorgestor.backend.repository;

import com.jorgestor.backend.model.Examen;
import com.jorgestor.backend.model.EstadoExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {
    List<Examen> findByEstado(EstadoExamen estado);
    List<Examen> findByAlumnoIdAndEstado(Long alumnoId, EstadoExamen estado);
}
