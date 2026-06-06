package com.jorgestor.backend.dto;

import java.util.Map;

public class GeneracionExitoDTO {
    private Integer totalPlantillas;
    private Map<Long, Integer> resumenPorGrado; // gradoId -> numPlantillas

    public GeneracionExitoDTO() {}

    public GeneracionExitoDTO(Integer totalPlantillas, Map<Long, Integer> resumenPorGrado) {
        this.totalPlantillas = totalPlantillas;
        this.resumenPorGrado = resumenPorGrado;
    }

    public Integer getTotalPlantillas() { return totalPlantillas; }
    public void setTotalPlantillas(Integer totalPlantillas) { this.totalPlantillas = totalPlantillas; }
    public Map<Long, Integer> getResumenPorGrado() { return resumenPorGrado; }
    public void setResumenPorGrado(Map<Long, Integer> resumenPorGrado) { this.resumenPorGrado = resumenPorGrado; }
}
