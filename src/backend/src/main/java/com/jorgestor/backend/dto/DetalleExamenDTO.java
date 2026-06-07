package com.jorgestor.backend.dto;

import java.util.List;

public class DetalleExamenDTO {
    private Long examenId;
    private String alumno;
    private Double notaFinal;
    private List<PreguntaDetalleDTO> preguntas;

    public DetalleExamenDTO(Long examenId, String alumno, Double notaFinal, List<PreguntaDetalleDTO> preguntas) {
        this.examenId = examenId;
        this.alumno = alumno;
        this.notaFinal = notaFinal;
        this.preguntas = preguntas;
    }

    // Getters
    public Long getExamenId() { return examenId; }
    public String getAlumno() { return alumno; }
    public Double getNotaFinal() { return notaFinal; }
    public List<PreguntaDetalleDTO> getPreguntas() { return preguntas; }

    public static class PreguntaDetalleDTO {
        private String enunciado;
        private String respuestaElegida;
        private boolean esCorrecta;
        private String respuestaCorrecta;

        public PreguntaDetalleDTO(String enunciado, String respuestaElegida, boolean esCorrecta, String respuestaCorrecta) {
            this.enunciado = enunciado;
            this.respuestaElegida = respuestaElegida;
            this.esCorrecta = esCorrecta;
            this.respuestaCorrecta = respuestaCorrecta;
        }

        // Getters
        public String getEnunciado() { return enunciado; }
        public String getRespuestaElegida() { return respuestaElegida; }
        public boolean isEsCorrecta() { return esCorrecta; }
        public String getRespuestaCorrecta() { return respuestaCorrecta; }
    }
}
