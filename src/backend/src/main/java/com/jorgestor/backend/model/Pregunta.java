package com.jorgestor.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "preguntas")
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String enunciado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tema tema;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DificultadPregunta dificultad;

    public Pregunta() {}

    public Pregunta(String enunciado, Tema tema, DificultadPregunta dificultad) {
        this.enunciado = enunciado;
        this.tema = tema;
        this.dificultad = dificultad;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }
    public Tema getTema() { return tema; }
    public void setTema(Tema tema) { this.tema = tema; }
    public DificultadPregunta getDificultad() { return dificultad; }
    public void setDificultad(DificultadPregunta dificultad) { this.dificultad = dificultad; }
}
