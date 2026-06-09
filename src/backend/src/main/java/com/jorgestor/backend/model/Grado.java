package com.jorgestor.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grados")
public class Grado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String titulo;

    @ManyToMany(mappedBy = "grados")
    private List<Asignatura> asignaturas = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id", nullable = true)
    private Usuario profesor;

    public Grado() {}

    public Grado(String codigo, String titulo) {
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
    public List<Asignatura> getAsignaturas() { return asignaturas; }
    public void setAsignaturas(List<Asignatura> asignaturas) { this.asignaturas = asignaturas; }
    public Usuario getProfesor() { return profesor; }
    public void setProfesor(Usuario profesor) { this.profesor = profesor; }
}
