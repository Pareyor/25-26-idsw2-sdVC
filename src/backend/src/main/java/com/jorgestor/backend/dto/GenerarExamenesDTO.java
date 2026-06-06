package com.jorgestor.backend.dto;

import com.jorgestor.backend.model.TipoExamen;
import java.util.List;

public class GenerarExamenesDTO {
    private Long asignaturaId;
    private TipoExamen tipoExamen;
    private List<String> temas;
    private List<ConfigGradoDTO> configuracionesGrado;

    public GenerarExamenesDTO() {}

    public Long getAsignaturaId() { return asignaturaId; }
    public void setAsignaturaId(Long asignaturaId) { this.asignaturaId = asignaturaId; }
    public TipoExamen getTipoExamen() { return tipoExamen; }
    public void setTipoExamen(TipoExamen tipoExamen) { this.tipoExamen = tipoExamen; }
    public List<String> getTemas() { return temas; }
    public void setTemas(List<String> temas) { this.temas = temas; }
    public List<ConfigGradoDTO> getConfiguracionesGrado() { return configuracionesGrado; }
    public void setConfiguracionesGrado(List<ConfigGradoDTO> configuracionesGrado) { this.configuracionesGrado = configuracionesGrado; }
}
