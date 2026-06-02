package com.jorgestor.backend.dto;

public class AlumnoDTO {
    private Long id;
    private String niu;
    private String nombre;
    private String apellidos;

    public AlumnoDTO() {}

    public AlumnoDTO(Long id, String niu, String nombre, String apellidos) {
        this.id = id;
        this.niu = niu;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNiu() { return niu; }
    public void setNiu(String niu) { this.niu = niu; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
}
