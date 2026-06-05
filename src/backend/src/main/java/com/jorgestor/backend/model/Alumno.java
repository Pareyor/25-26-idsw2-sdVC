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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grado_id", nullable = false)
    private Grado grado;

    public Alumno() {}

    public Alumno(String niu, String nombre, String apellidos, Grado grado) {
        this.niu = niu;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.grado = grado;
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
    public Grado getGrado() { return grado; }
    public void setGrado(Grado grado) { this.grado = grado; }
}
