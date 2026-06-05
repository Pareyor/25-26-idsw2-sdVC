package com.jorgestor.backend.dto;

import com.jorgestor.backend.model.DificultadPregunta;
import com.jorgestor.backend.model.Tema;
import java.util.List;

public class PreguntaDTO {
    private Long id;
    private String enunciado;
    private Tema tema;
    private DificultadPregunta dificultad;
    private Long asignaturaId;
    private List<RespuestaDTO> respuestas;

    public PreguntaDTO() {}

    public PreguntaDTO(Long id, String enunciado, Tema tema, DificultadPregunta dificultad, Long asignaturaId, List<RespuestaDTO> respuestas) {
        this.id = id;
        this.enunciado = enunciado;
        this.tema = tema;
        this.dificultad = dificultad;
        this.asignaturaId = asignaturaId;
        this.respuestas = respuestas;
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
    public Long getAsignaturaId() { return asignaturaId; }
    public void setAsignaturaId(Long asignaturaId) { this.asignaturaId = asignaturaId; }
    public List<RespuestaDTO> getRespuestas() { return respuestas; }
    public void setRespuestas(List<RespuestaDTO> respuestas) { this.respuestas = respuestas; }
}
