package com.jorgestor.backend.repository;

import com.jorgestor.backend.model.ExamenBorrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamenBorradorRepository extends JpaRepository<ExamenBorrador, Long> {
}
