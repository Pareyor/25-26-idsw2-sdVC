package com.jorgestor.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "alumnos")
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String niu;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    public Alumno() {}

    public Alumno(String niu, String nombre, String apellidos) {
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
