package com.jorgestor.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "examen_borradores")
public class ExamenBorrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignatura_id", nullable = false)
    private Asignatura asignatura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grado_id", nullable = false)
    private Grado grado;

    @Enumerated(EnumType.STRING)
    private TipoExamen tipoExamen;

    @OneToMany(mappedBy = "examenBorrador", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<ExamenBorradorPregunta> preguntas;

    private String clave;

    public ExamenBorrador() {}

    public ExamenBorrador(Asignatura asignatura, Grado grado, TipoExamen tipoExamen, String clave) {
        this.asignatura = asignatura;
        this.grado = grado;
        this.tipoExamen = tipoExamen;
        this.clave = clave;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Asignatura getAsignatura() { return asignatura; }
    public void setAsignatura(Asignatura asignatura) { this.asignatura = asignatura; }
    public Grado getGrado() { return grado; }
    public void setGrado(Grado grado) { this.grado = grado; }
    public TipoExamen getTipoExamen() { return tipoExamen; }
    public void setTipoExamen(TipoExamen tipoExamen) { this.tipoExamen = tipoExamen; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
    public java.util.List<ExamenBorradorPregunta> getPreguntas() { return preguntas; }
    public void setPreguntas(java.util.List<ExamenBorradorPregunta> preguntas) { this.preguntas = preguntas; }
}
