package com.jorgestor.backend.dto;

import com.jorgestor.backend.model.Tema;
import java.util.List;

public class GenerarExamenesDTO {
    private Long asignaturaId;
    private String evaluacion;
    private List<Tema> temas;
    private Integer numPreguntas;
    private List<ConfigGradoDTO> configuracionesGrado;

    public GenerarExamenesDTO() {}

    public Long getAsignaturaId() { return asignaturaId; }
    public void setAsignaturaId(Long asignaturaId) { this.asignaturaId = asignaturaId; }
    public String getEvaluacion() { return evaluacion; }
    public void setEvaluacion(String evaluacion) { this.evaluacion = evaluacion; }
    public List<Tema> getTemas() { return temas; }
    public void setTemas(List<Tema> temas) { this.temas = temas; }
    public Integer getNumPreguntas() { return numPreguntas; }
    public void setNumPreguntas(Integer numPreguntas) { this.numPreguntas = numPreguntas; }
    public List<ConfigGradoDTO> getConfiguracionesGrado() { return configuracionesGrado; }
    public void setConfiguracionesGrado(List<ConfigGradoDTO> configuracionesGrado) { this.configuracionesGrado = configuracionesGrado; }
}
