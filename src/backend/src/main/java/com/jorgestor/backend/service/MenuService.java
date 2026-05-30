package com.jorgestor.backend.service;

import com.jorgestor.backend.dto.MenuOptionDTO;
import com.jorgestor.backend.model.Role;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    public List<MenuOptionDTO> getMenuOptions(String role) {
        List<MenuOptionDTO> options = new ArrayList<>();

        if (Role.ROLE_ADMIN.name().equals(role)) {
            options.add(new MenuOptionDTO("Gestión de Docentes", "/docentes", "users"));
            options.add(new MenuOptionDTO("Cerrar Sesión", "/logout", "log-out"));
        } else if (Role.ROLE_DOCENTE.name().equals(role)) {
            options.add(new MenuOptionDTO("Ver Grados", "/grados", "graduation-cap"));
            options.add(new MenuOptionDTO("Ver Asignaturas", "/asignaturas", "book"));
            options.add(new MenuOptionDTO("Ver Alumnos", "/alumnos", "user-group"));
            options.add(new MenuOptionDTO("Ver Preguntas", "/preguntas", "help-circle"));
            options.add(new MenuOptionDTO("Generar Exámenes", "/examenes/generar", "file-text"));
            options.add(new MenuOptionDTO("Corregir Exámenes", "/examenes/corregir", "check-square"));
            options.add(new MenuOptionDTO("Configuración Global", "/configuracion", "settings"));
            options.add(new MenuOptionDTO("Cerrar Sesión", "/logout", "log-out"));
        }

        return options;
    }
}
