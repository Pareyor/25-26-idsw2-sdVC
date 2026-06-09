package com.jorgestor.backend.repository;

import com.jorgestor.backend.model.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    Optional<Asignatura> findByCodigo(String codigo);
    Optional<Asignatura> findByCodigoAndProfesorId(String codigo, Long profesorId);
    List<Asignatura> findByProfesorId(Long profesorId);
}
