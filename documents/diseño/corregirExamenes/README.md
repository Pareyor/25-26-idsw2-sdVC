# Diseño de Caso de Uso: Corregir Exámenes

## 1. Descripción
Este caso de uso permite al docente realizar la corrección de los exámenes previamente asignados y persistidos en el sistema. El sistema permite la corrección de exámenes de forma individual, masiva por asignatura o masiva global (todos los pendientes), utilizando una lógica interna para calcular la nota final y actualizar el estado del examen.

## 2. Actores
- **Docente**: Usuario que supervisa la corrección y gestiona las notas de los exámenes de sus asignaturas.

## 3. Patrones Aplicados
- **Service-to-Service**: El `ExamenController` delega la lógica de negocio al `ExamenService`.
- **Estrategias de Corrección**: Soporte para múltiples niveles de corrección (individual, grupal, masiva).

## 4. Participantes

### Backend
- **ExamenController**:
  - `GET /api/examenes/corregir/listar`: Lista exámenes pendientes.
  - `POST /api/examenes/corregir/{examenId}`: Corrige un examen específico.
  - `POST /api/examenes/corregir/asignatura/{id}`: Corrección masiva por asignatura.
  - `POST /api/examenes/corregir/todos`: Corrección masiva global.
- **ExamenService**: Contiene la lógica para actualizar el estado del examen y calcular la nota.

### Frontend
- **VistaCorreccionExamenes (React)**: Interfaz para listar, visualizar detalles y ejecutar la corrección de exámenes.

## 5. Lógica de Control
1. El docente accede a la vista de corrección.
2. El sistema recupera los exámenes asignados pero no corregidos (`listar`).
3. El docente selecciona un modo de corrección (individual, asignatura o todos).
4. El sistema ejecuta la lógica de corrección, actualiza el estado (`CORREGIDO`) y la nota.
5. El sistema confirma la operación exitosa.

## 6. Diagrama de Secuencia
![Diagrama de Secuencia](../../../modelosUML/diseño/corregirExamenes/corregirExamenes.puml)
