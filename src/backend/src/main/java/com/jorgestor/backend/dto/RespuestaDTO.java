package com.jorgestor.backend.dto;

public class RespuestaDTO {
    private Long id;
    private String opcion;
    private boolean esCorrecta;

    public RespuestaDTO() {}

    public RespuestaDTO(Long id, String opcion, boolean esCorrecta) {
        this.id = id;
        this.opcion = opcion;
        this.esCorrecta = esCorrecta;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOpcion() { return opcion; }
    public void setOpcion(String opcion) { this.opcion = opcion; }
    public boolean isEsCorrecta() { return esCorrecta; }
    public void setEsCorrecta(boolean esCorrecta) { this.esCorrecta = esCorrecta; }
}
