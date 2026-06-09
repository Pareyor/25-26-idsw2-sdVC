package com.jorgestor.backend.dto;

import com.jorgestor.backend.model.TipoExamen;

public class ExamenBorradorDTO {
    private Long id;
    private Long asignaturaId;
    private Long gradoId;
    private TipoExamen tipoExamen;
    private String clave;
    private int numPreguntas;

    public ExamenBorradorDTO() {}

    public ExamenBorradorDTO(Long id, Long asignaturaId, Long gradoId, TipoExamen tipoExamen, String clave, int numPreguntas) {
        this.id = id;
        this.asignaturaId = asignaturaId;
        this.gradoId = gradoId;
        this.tipoExamen = tipoExamen;
        this.clave = clave;
        this.numPreguntas = numPreguntas;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAsignaturaId() { return asignaturaId; }
    public void setAsignaturaId(Long asignaturaId) { this.asignaturaId = asignaturaId; }
    public Long getGradoId() { return gradoId; }
    public void setGradoId(Long gradoId) { this.gradoId = gradoId; }
    public TipoExamen getTipoExamen() { return tipoExamen; }
    public void setTipoExamen(TipoExamen tipoExamen) { this.tipoExamen = tipoExamen; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public int getNumPreguntas() { return numPreguntas; }
    public void setNumPreguntas(int numPreguntas) { this.numPreguntas = numPreguntas; }
}
