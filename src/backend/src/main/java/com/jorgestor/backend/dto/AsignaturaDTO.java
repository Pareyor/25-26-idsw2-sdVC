package com.jorgestor.backend.dto;

public class AsignaturaDTO {
    private Long id;
    private String codigo;
    private String titulo;
    private String cursoAcademico;
    private Long gradoId;
    private Long profesorId;

    public AsignaturaDTO() {}

    public AsignaturaDTO(Long id, String codigo, String titulo, String cursoAcademico, Long gradoId) {
        this.id = id;
        this.codigo = codigo;
        this.titulo = titulo;
        this.cursoAcademico = cursoAcademico;
        this.gradoId = gradoId;
    }

    public AsignaturaDTO(Long id, String codigo, String titulo, String cursoAcademico, Long gradoId, Long profesorId) {
        this.id = id;
        this.codigo = codigo;
        this.titulo = titulo;
        this.cursoAcademico = cursoAcademico;
        this.gradoId = gradoId;
        this.profesorId = profesorId;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getCursoAcademico() { return cursoAcademico; }
    public void setCursoAcademico(String cursoAcademico) { this.cursoAcademico = cursoAcademico; }
    public Long getGradoId() { return gradoId; }
    public void setGradoId(Long gradoId) { this.gradoId = gradoId; }
    public Long getProfesorId() { return profesorId; }
    public void setProfesorId(Long profesorId) { this.profesorId = profesorId; }
}
