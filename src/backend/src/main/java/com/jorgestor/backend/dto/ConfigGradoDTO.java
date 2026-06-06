package com.jorgestor.backend.dto;

import java.util.List;

public class ConfigGradoDTO {
    private Long gradoId;
    private Integer numExamenes;
    private Integer numTipos;
    private Integer proporcionFacil;
    private Integer proporcionMedia;
    private Integer proporcionDificil;

    public ConfigGradoDTO() {}

    public Long getGradoId() { return gradoId; }
    public void setGradoId(Long gradoId) { this.gradoId = gradoId; }
    public Integer getNumExamenes() { return numExamenes; }
    public void setNumExamenes(Integer numExamenes) { this.numExamenes = numExamenes; }
    public Integer getNumTipos() { return numTipos; }
    public void setNumTipos(Integer numTipos) { this.numTipos = numTipos; }
    public Integer getProporcionFacil() { return proporcionFacil; }
    public void setProporcionFacil(Integer proporcionFacil) { this.proporcionFacil = proporcionFacil; }
    public Integer getProporcionMedia() { return proporcionMedia; }
    public void setProporcionMedia(Integer proporcionMedia) { this.proporcionMedia = proporcionMedia; }
    public Integer getProporcionDificil() { return proporcionDificil; }
    public void setProporcionDificil(Integer proporcionDificil) { this.proporcionDificil = proporcionDificil; }
}
