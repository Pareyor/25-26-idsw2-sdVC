# Jorgestor > editarAlumno > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Análisis](/documents/analisis/editarAlumno/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0
- **Fecha**: 2026-06-03
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica de la edición de datos de un alumno existente por parte del Docente. Se aplica el patrón "El Gordo" para permitir la edición integral de los campos (Nombre, Apellidos, Grado vinculado).

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../images/diseño/editarAlumno/secuencia.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/editarAlumno/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente `AlumnoEdit.tsx` que gestiona la carga de datos inicial y el formulario de modificación.
- **AlumnoController**: Endpoints `GET /api/alumnos/{id}` y `PUT /api/alumnos/{id}` protegidos por `@PreAuthorize("hasRole('DOCENTE')")`.
- **AlumnoService**: Lógica para recuperar la entidad, validar cambios y persistir la actualización, validando el Grado a través de `GradoService`.
- **AlumnoRepository**: Interface para interactuar con la persistencia de los alumnos.
- **GradoService**: Servicio responsable de las operaciones sobre grados.
- **AlumnoDTO**: Clase para transferir los datos del alumno entre capas.

## Decisiones de diseño

- **Carga Previa**: Se realiza una petición GET inicial para asegurar que el usuario edita la versión más reciente del alumno.
- **Validación de Grado**: Si el Docente cambia el Grado del alumno, se utiliza `GradoService` para asegurar la validez del nuevo ID antes de aplicar cambios.
- **Integridad**: El servicio verifica la existencia del alumno antes de actualizar (`404 Not Found` si no existe).
- **Seguridad**: Solo usuarios con el rol `ROLE_DOCENTE` pueden realizar estas operaciones.
- **Flujo de Usuario**: Tras guardar los cambios, el sistema redirige al listado general para confirmar visualmente la actualización.
- **Patrón de Edición**: Se utiliza el patrón "El Gordo", permitiendo la edición de todos los campos visibles en una única operación.
