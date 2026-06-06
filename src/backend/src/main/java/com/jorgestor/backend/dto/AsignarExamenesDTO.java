package com.jorgestor.backend.dto;

import java.util.List;

public class AsignarExamenesDTO {
    private List<Long> alumnoIds;

    public AsignarExamenesDTO() {}

    public List<Long> getAlumnoIds() { return alumnoIds; }
    public void setAlumnoIds(List<Long> alumnoIds) { this.alumnoIds = alumnoIds; }
}
