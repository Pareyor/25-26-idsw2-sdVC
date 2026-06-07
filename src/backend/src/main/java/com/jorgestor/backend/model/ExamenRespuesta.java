package com.jorgestor.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "examen_respuestas")
public class ExamenRespuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examen_id", nullable = false)
    private Examen examen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pregunta_id", nullable = false)
    private Pregunta pregunta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "respuesta_id", nullable = false)
    private Respuesta respuesta;

    public ExamenRespuesta() {}

    public ExamenRespuesta(Examen examen, Pregunta pregunta, Respuesta respuesta) {
        this.examen = examen;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Examen getExamen() { return examen; }
    public void setExamen(Examen examen) { this.examen = examen; }
    public Pregunta getPregunta() { return pregunta; }
    public void setPregunta(Pregunta pregunta) { this.pregunta = pregunta; }
    public Respuesta getRespuesta() { return respuesta; }
    public void setRespuesta(Respuesta respuesta) { this.respuesta = respuesta; }
}
