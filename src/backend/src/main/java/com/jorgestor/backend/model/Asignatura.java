package com.jorgestor.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "asignaturas")
public class Asignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false)
    private String titulo;

    @Column(name = "curso_academico", nullable = false)
    private String cursoAcademico;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "asignatura_grado",
        joinColumns = @JoinColumn(name = "asignatura_id"),
        inverseJoinColumns = @JoinColumn(name = "grado_id")
    )
    private List<Grado> grados = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id", nullable = true)
    private Usuario profesor;

    public Asignatura() {}

    public Asignatura(String codigo, String titulo, String cursoAcademico, List<Grado> grados) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.cursoAcademico = cursoAcademico;
        this.grados = grados;
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
    public List<Grado> getGrados() { return grados; }
    public void setGrados(List<Grado> grados) { this.grados = grados; }
    public Usuario getProfesor() { return profesor; }
    public void setProfesor(Usuario profesor) { this.profesor = profesor; }
}
