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
            options.add(new MenuOptionDTO("Gestión de Docentes", "/docentes", "Users"));
            options.add(new MenuOptionDTO("Cerrar Sesión", "/logout", "LogOut"));
        } else if (Role.ROLE_DOCENTE.name().equals(role)) {
            options.add(new MenuOptionDTO("Grados", "/grados", "GraduationCap"));
            options.add(new MenuOptionDTO("Asignaturas", "/asignaturas", "BookOpen"));
            options.add(new MenuOptionDTO("Alumnos", "/alumnos", "Users"));
            options.add(new MenuOptionDTO("Preguntas", "/preguntas", "HelpCircle"));
            options.add(new MenuOptionDTO("Generar Exámenes", "/examenes/generar", "FileText"));
            options.add(new MenuOptionDTO("Corregir Exámenes", "/examenes/corregir", "CheckCircle"));
            options.add(new MenuOptionDTO("Cerrar Sesión", "/logout", "LogOut"));
        }

        return options;
    }
}
