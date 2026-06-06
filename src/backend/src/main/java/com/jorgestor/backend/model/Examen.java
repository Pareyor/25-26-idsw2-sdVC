package com.jorgestor.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "examenes")
public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignatura_id", nullable = false)
    private Asignatura asignatura;

    @Enumerated(EnumType.STRING)
    private TipoExamen tipoExamen;

    private String clave;

    public Examen() {}

    public Examen(Alumno alumno, Asignatura asignatura, TipoExamen tipoExamen, String clave) {
        this.alumno = alumno;
        this.asignatura = asignatura;
        this.tipoExamen = tipoExamen;
        this.clave = clave;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Alumno getAlumno() { return alumno; }
    public void setAlumno(Alumno alumno) { this.alumno = alumno; }
    public Asignatura getAsignatura() { return asignatura; }
    public void setAsignatura(Asignatura asignatura) { this.asignatura = asignatura; }
    public TipoExamen getTipoExamen() { return tipoExamen; }
    public void setTipoExamen(TipoExamen tipoExamen) { this.tipoExamen = tipoExamen; }
    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}
