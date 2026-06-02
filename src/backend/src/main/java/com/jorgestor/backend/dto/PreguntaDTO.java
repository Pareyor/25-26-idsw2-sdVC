package com.jorgestor.backend.dto;

import com.jorgestor.backend.model.DificultadPregunta;
import com.jorgestor.backend.model.Tema;

public class PreguntaDTO {
    private Long id;
    private String enunciado;
    private Tema tema;
    private DificultadPregunta dificultad;

    public PreguntaDTO() {}

    public PreguntaDTO(Long id, String enunciado, Tema tema, DificultadPregunta dificultad) {
        this.id = id;
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
