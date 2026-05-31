# Jorgestor > verAlumnos > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Análisis](/documents/analisis/verAlumnos/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0
- **Fecha**: 2026-05-31
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica del listado de alumnos para el Docente. Este diseño asegura que los usuarios con rol `ROLE_DOCENTE` puedan visualizar la lista de alumnos matriculados.

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../modelosUML/diseño/verAlumnos/secuencia.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/verAlumnos/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente `AlumnoList.tsx` que consume el endpoint `/api/alumnos`.
- **AlumnoController**: Endpoint `GET /api/alumnos` protegido por `@PreAuthorize("hasRole('DOCENTE')")`.
- **AlumnoService**: Lógica de negocio para la recuperación de alumnos.
- **AlumnoRepository**: Interface JPA para la persistencia de la entidad `Alumno`.
- **AlumnoDTO**: Objeto de transferencia para los datos del alumno (`id`, `niu`, `nombre`, `apellidos`).

## Decisiones de diseño

- **Entidad Alumno**: Se implementará la entidad `Alumno` con los campos `niu`, `nombre` y `apellidos` según el modelo del dominio.
- **Seguridad**: Acceso restringido a usuarios con rol docente.
- **Consistencia**: Mantiene la estructura de listado simple para facilitar la navegación rápida.
