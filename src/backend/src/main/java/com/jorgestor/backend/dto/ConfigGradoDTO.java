package com.jorgestor.backend.dto;

import java.util.List;

public class ConfigGradoDTO {
    private Long gradoId;
    private Integer numExamenes;
    private Integer numPreguntas;
    private Integer proporcionFacil;
    private Integer proporcionMedia;
    private Integer proporcionDificil;

    public ConfigGradoDTO() {}

    public Long getGradoId() { return gradoId; }
    public void setGradoId(Long gradoId) { this.gradoId = gradoId; }
    public Integer getNumExamenes() { return numExamenes; }
    public void setNumExamenes(Integer numExamenes) { this.numExamenes = numExamenes; }
    public Integer getNumPreguntas() { return numPreguntas; }
    public void setNumPreguntas(Integer numPreguntas) { this.numPreguntas = numPreguntas; }
    public Integer getProporcionFacil() { return proporcionFacil; }
    public void setProporcionFacil(Integer proporcionFacil) { this.proporcionFacil = proporcionFacil; }
    public Integer getProporcionMedia() { return proporcionMedia; }
    public void setProporcionMedia(Integer proporcionMedia) { this.proporcionMedia = proporcionMedia; }
    public Integer getProporcionDificil() { return proporcionDificil; }
    public void setProporcionDificil(Integer proporcionDificil) { this.proporcionDificil = proporcionDificil; }
}
