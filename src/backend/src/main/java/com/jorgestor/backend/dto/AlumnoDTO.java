package com.jorgestor.backend.dto;

public class AlumnoDTO {
    private Long id;
    private String dni;
    private String nombre;
    private String apellidos;
    private Long gradoId;
    private String curso;

    public AlumnoDTO() {}

    public AlumnoDTO(Long id, String dni, String nombre, String apellidos, Long gradoId, String curso) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.gradoId = gradoId;
        this.curso = curso;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public Long getGradoId() { return gradoId; }
    public void setGradoId(Long gradoId) { this.gradoId = gradoId; }
    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }
}
