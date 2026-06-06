# Diseño de Caso de Uso: Asignar Exámenes

## 1. Descripción
Este caso de uso convierte los borradores de exámenes generados temporalmente (en sesión) en instancias reales persistentes vinculadas a los alumnos correspondientes en la base de datos.

## 2. Actores
- **Docente**: Usuario que valida y confirma la asignación final.

## 3. Patrones Aplicados
- **Service-to-Service**: `ExamenController` orquesta la persistencia coordinando con `ExamenService`, `AlumnoService` y `ExamenSessionService`.
- **Ephemeral-to-Persistent Transition**: El proceso toma los datos guardados en la sesión y los escribe definitivamente en la base de datos.

## 4. Participantes

### Backend
- **ExamenController**: Endpoint `POST /api/examenes/asignar`. Gestiona la transición de estado.
- **ExamenService**: Ejecuta la lógica de persistencia de las instancias de examen en la base de datos.
- **AlumnoService**: Recupera los alumnos para vincularlos con las plantillas generadas.
- **ExamenSessionService**: Proporciona los datos temporales y, posteriormente, limpia la sesión.

### Frontend
- **AsignarExamenesView (React)**: Muestra el resumen de la generación y permite la confirmación final.

## 5. Lógica de Control
1. El docente confirma la asignación desde la vista de previsualización.
2. Se recuperan los borradores de la sesión.
3. Se obtienen los alumnos de la asignatura desde `AlumnoService`.
4. Se ejecutan las operaciones de inserción en la base de datos para crear los registros de `Examen`.
5. Se eliminan los borradores de la sesión.

## 6. Diagrama de Secuencia
![Diagrama de Secuencia](../../../modelosUML/diseño/asignarExamenes/asignarExamenes.puml)
