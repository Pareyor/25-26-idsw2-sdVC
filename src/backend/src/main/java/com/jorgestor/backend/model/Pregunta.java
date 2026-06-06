package com.jorgestor.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "preguntas")
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String enunciado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPregunta tipo;

    @Column(nullable = false)
    private String tema;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DificultadPregunta dificultad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignatura_id", nullable = true)
    private Asignatura asignatura;

    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respuesta> respuestas = new ArrayList<>();

    public Pregunta() {}

    public Pregunta(String enunciado, TipoPregunta tipo, String tema, DificultadPregunta dificultad, Asignatura asignatura) {
        this.enunciado = enunciado;
        this.tipo = tipo;
        this.tema = tema;
        this.dificultad = dificultad;
        this.asignatura = asignatura;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }
    public TipoPregunta getTipo() { return tipo; }
    public void setTipo(TipoPregunta tipo) { this.tipo = tipo; }
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
    public DificultadPregunta getDificultad() { return dificultad; }
    public void setDificultad(DificultadPregunta dificultad) { this.dificultad = dificultad; }
    public Asignatura getAsignatura() { return asignatura; }
    public void setAsignatura(Asignatura asignatura) { this.asignatura = asignatura; }
    public List<Respuesta> getRespuestas() { return respuestas; }
    public void setRespuestas(List<Respuesta> respuestas) { this.respuestas = respuestas; }
}
