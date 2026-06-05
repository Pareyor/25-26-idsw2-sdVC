package com.jorgestor.backend.dto;

public class AlumnoDTO {
    private Long id;
    private String dni;
    private String nombre;
    private String apellidos;
    private Long gradoId;

    public AlumnoDTO() {}

    public AlumnoDTO(Long id, String dni, String nombre, String apellidos, Long gradoId) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.gradoId = gradoId;
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
}
