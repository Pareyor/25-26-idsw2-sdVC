# Diseño de Caso de Uso: Generar Exámenes

## 1. Descripción
Este caso de uso permite al docente generar un conjunto de exámenes personalizados para los alumnos matriculados en una asignatura. La generación se basa en parámetros de configuración (temas, número de preguntas, dificultad) y se realiza de forma **efímera** (en memoria/sesión) para permitir una previsualización o cancelación antes de la persistencia definitiva.

## 2. Actores
- **Docente**: Usuario que configura y solicita la generación.

## 3. Patrones Aplicados
- **Service-to-Service**: `ExamenService` no accede a repositorios ajenos; colabora con `AsignaturaService` y `PreguntaService`.
- **Ephemeral Draft (Borrador Efímero)**: Los exámenes generados no se guardan en la base de datos inmediatamente, sino en la `HttpSession` a través de un servicio dedicado.
- **DTO (Data Transfer Object)**: Uso de objetos específicos para la comunicación entre capas.

## 4. Participantes

### Backend
- **ExamenController**: Expone el endpoint `POST /api/examenes/generar`. Extrae el `docenteId` del token JWT.
- **ExamenService**: Orquestador principal. Valida criterios, selecciona preguntas aleatoriamente y construye los objetos de examen.
- **AsignaturaService**: Proporciona la información de la asignatura y la lista de alumnos matriculados.
- **PreguntaService**: Proporciona el banco de preguntas filtrado por temas y validado.
- **ExamenSessionService**: Gestiona la persistencia temporal en la sesión del servidor (`HttpSession`).

### Frontend
- **GenerarExamenForm (React)**: Formulario de configuración (Asignatura, Temas, Cantidad).
- **ExamenService (TS)**: Servicio de cliente que invoca la API.

## 5. Lógica de Generación
1. **Validación**: Se comprueba que existan suficientes preguntas en la batería de la asignatura para los temas seleccionados.
2. **Selección Aleatoria**: Por cada examen (uno por alumno), se seleccionan $N$ preguntas de forma aleatoria.
3. **Aleatorización**: Se desordenan tanto las preguntas como las opciones de respuesta (si la configuración lo permite).
4. **Clave Alfanumérica**: Se genera una clave de acceso única para cada examen.

## 6. Diagrama de Secuencia
![Diagrama de Secuencia](../../../modelosUML/diseño/generarExamenes/generarExamenes.puml)
