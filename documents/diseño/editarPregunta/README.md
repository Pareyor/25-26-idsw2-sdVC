# Jorgestor > editarPregunta > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Análisis](/documents/analisis/editarPregunta/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0
- **Fecha**: 2026-06-03
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica de la edición de datos de una pregunta existente por parte del Docente. Se aplica el patrón "El Gordo" para permitir la edición integral de los campos (Enunciado, Tema, Dificultad).

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../modelosUML/diseño/editarPregunta/secuencia.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/editarPregunta/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente `PreguntaEdit.tsx` que gestiona la carga de datos inicial y el formulario de modificación.
- **PreguntaController**: Endpoints `GET /api/preguntas/{id}` y `PUT /api/preguntas/{id}` protegidos por `@PreAuthorize("hasRole('DOCENTE')")`.
- **PreguntaService**: Lógica para recuperar la entidad, validar la asignatura a través de `AsignaturaService` y persistir la actualización.
- **PreguntaRepository**: Interface para interactuar con la persistencia de las preguntas.
- **AsignaturaService**: Servicio responsable de las operaciones sobre asignaturas.
- **AsignaturaRepository**: Interface para validar la existencia de la asignatura vinculada.
- **PreguntaDTO**: Clase para transferir los datos de la pregunta entre capas.

## Decisiones de diseño

- **Carga Previa**: Se realiza una petición GET inicial para asegurar que el usuario edita la versión más reciente de la pregunta.
- **Validación de Asignatura**: Se utiliza `AsignaturaService` para asegurar que, si se referencia una asignatura, esta sea válida antes de aplicar cambios.
- **Integridad**: El servicio verifica la existencia de la pregunta antes de actualizar (`404 Not Found` si no existe).
- **Seguridad**: Solo usuarios con el rol `ROLE_DOCENTE` pueden realizar estas operaciones.
- **Flujo de Usuario**: Tras guardar los cambios, el sistema redirige al listado general para confirmar visualmente la actualización.
- **Patrón de Edición**: Se utiliza el patrón "El Gordo", permitiendo la edición de todos los campos visibles en una única operación.
