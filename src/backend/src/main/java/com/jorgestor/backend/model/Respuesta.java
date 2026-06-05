package com.jorgestor.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "respuestas")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String opcion;

    @Column(nullable = false)
    private boolean esCorrecta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pregunta_id", nullable = false)
    private Pregunta pregunta;

    public Respuesta() {}

    public Respuesta(String opcion, boolean esCorrecta, Pregunta pregunta) {
        this.opcion = opcion;
        this.esCorrecta = esCorrecta;
        this.pregunta = pregunta;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOpcion() { return opcion; }
    public void setOpcion(String opcion) { this.opcion = opcion; }
    public boolean isEsCorrecta() { return esCorrecta; }
    public void setEsCorrecta(boolean esCorrecta) { this.esCorrecta = esCorrecta; }
    public Pregunta getPregunta() { return pregunta; }
    public void setPregunta(Pregunta pregunta) { this.pregunta = pregunta; }
}
