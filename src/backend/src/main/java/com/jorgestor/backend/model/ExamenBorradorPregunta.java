package com.jorgestor.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "examen_borrador_preguntas")
public class ExamenBorradorPregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examen_borrador_id", nullable = false)
    private ExamenBorrador examenBorrador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pregunta_id", nullable = false)
    private Pregunta pregunta;

    public ExamenBorradorPregunta() {}

    public ExamenBorradorPregunta(ExamenBorrador examenBorrador, Pregunta pregunta) {
        this.examenBorrador = examenBorrador;
        this.pregunta = pregunta;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ExamenBorrador getExamenBorrador() { return examenBorrador; }
    public void setExamenBorrador(ExamenBorrador examenBorrador) { this.examenBorrador = examenBorrador; }
    public Pregunta getPregunta() { return pregunta; }
    public void setPregunta(Pregunta pregunta) { this.pregunta = pregunta; }
}
