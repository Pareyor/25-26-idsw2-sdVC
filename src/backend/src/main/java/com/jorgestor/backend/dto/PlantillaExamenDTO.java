package com.jorgestor.backend.dto;

import java.util.List;
import java.util.UUID;

public class PlantillaExamenDTO {
    private String id;
    private Long gradoId;
    private Long asignaturaId;
    private String evaluacion;
    private List<PreguntaDTO> preguntas;
    private String clave;

    public PlantillaExamenDTO() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Long getGradoId() { return gradoId; }
    public void setGradoId(Long gradoId) { this.gradoId = gradoId; }
    public Long getAsignaturaId() { return asignaturaId; }
    public void setAsignaturaId(Long asignaturaId) { this.asignaturaId = asignaturaId; }
    public String getEvaluacion() { return evaluacion; }
    public void setEvaluacion(String evaluacion) { this.evaluacion = evaluacion; }
    public List<PreguntaDTO> getPreguntas() { return preguntas; }
    public void setPreguntas(List<PreguntaDTO> preguntas) { this.preguntas = preguntas; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}
