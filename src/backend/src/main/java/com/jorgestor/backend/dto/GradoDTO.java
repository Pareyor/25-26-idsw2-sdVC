package com.jorgestor.backend.dto;

public class GradoDTO {
    private Long id;
    private String codigo;
    private String titulo;

    public GradoDTO() {}

    public GradoDTO(Long id, String codigo, String titulo) {
        this.id = id;
        this.codigo = codigo;
        this.titulo = titulo;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
}
