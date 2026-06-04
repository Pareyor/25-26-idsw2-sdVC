package com.jorgestor.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "asignaturas")
public class Asignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String titulo;

    @Column(name = "curso_academico", nullable = false)
    private String cursoAcademico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grado_id", nullable = false)
    private Grado grado;

    public Asignatura() {}

    public Asignatura(String codigo, String titulo, String cursoAcademico, Grado grado) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.cursoAcademico = cursoAcademico;
        this.grado = grado;
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
    public Grado getGrado() { return grado; }
    public void setGrado(Grado grado) { this.grado = grado; }
}
