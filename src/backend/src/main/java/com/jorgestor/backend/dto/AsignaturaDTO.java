package com.jorgestor.backend.dto;

import java.util.List;
import java.util.Map;

public class AsignaturaDTO {
    private Long id;
    private String codigo;
    private String titulo;
    private String cursoAcademico;
    private List<Long> gradoIds;
    private Long profesorId;
    private Map<Long, Integer> alumnosPorGrado;

    public AsignaturaDTO() {}

    public AsignaturaDTO(Long id, String codigo, String titulo, String cursoAcademico, List<Long> gradoIds, Long profesorId, Map<Long, Integer> alumnosPorGrado) {
        this.id = id;
        this.codigo = codigo;
        this.titulo = titulo;
        this.cursoAcademico = cursoAcademico;
        this.gradoIds = gradoIds;
        this.profesorId = profesorId;
        this.alumnosPorGrado = alumnosPorGrado;
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
    public List<Long> getGradoIds() { return gradoIds; }
    public void setGradoIds(List<Long> gradoIds) { this.gradoIds = gradoIds; }
    public Long getProfesorId() { return profesorId; }
    public void setProfesorId(Long profesorId) { this.profesorId = profesorId; }
    public Map<Long, Integer> getAlumnosPorGrado() { return alumnosPorGrado; }
    public void setAlumnosPorGrado(Map<Long, Integer> alumnosPorGrado) { this.alumnosPorGrado = alumnosPorGrado; }
}
