package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.PlantillaExamenDTO;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExamenSessionService {

    private static final String EXAMENES_BORRADOR_KEY = "EXAMENES_BORRADOR";
    private final HttpSession httpSession;

    public ExamenSessionService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public void guardarBorradores(List<PlantillaExamenDTO> plantillas) {
        httpSession.setAttribute(EXAMENES_BORRADOR_KEY, plantillas);
    }

    @SuppressWarnings("unchecked")
    public List<PlantillaExamenDTO> obtenerBorradores() {
        List<PlantillaExamenDTO> plantillas = (List<PlantillaExamenDTO>) httpSession.getAttribute(EXAMENES_BORRADOR_KEY);
        return plantillas != null ? plantillas : new ArrayList<>();
    }

    public void limpiarBorradores() {
        httpSession.removeAttribute(EXAMENES_BORRADOR_KEY);
    }
}
