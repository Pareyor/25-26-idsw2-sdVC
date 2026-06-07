package com.jorgestor.backend.dto;

import java.util.List;

public class ConfigExportDTO {
    private List<AsignaturaDTO> asignaturas;
    private List<PreguntaDTO> preguntas;

    public ConfigExportDTO() {}

    public ConfigExportDTO(List<AsignaturaDTO> asignaturas, List<PreguntaDTO> preguntas) {
        this.asignaturas = asignaturas;
        this.preguntas = preguntas;
    }

    public List<AsignaturaDTO> getAsignaturas() { return asignaturas; }
    public void setAsignaturas(List<AsignaturaDTO> asignaturas) { this.asignaturas = asignaturas; }
    public List<PreguntaDTO> getPreguntas() { return preguntas; }
    public void setPreguntas(List<PreguntaDTO> preguntas) { this.preguntas = preguntas; }
}
