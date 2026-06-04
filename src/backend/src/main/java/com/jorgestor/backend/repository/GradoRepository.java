package com.jorgestor.backend.repository;

import com.jorgestor.backend.model.Grado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradoRepository extends JpaRepository<Grado, Long> {
    Optional<Grado> findByCodigo(String codigo);
}
