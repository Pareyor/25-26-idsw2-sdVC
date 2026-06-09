# Jorgestor > verAsignaturas > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Análisis](/documents/analisis/verAsignaturas/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0
- **Fecha**: 2026-05-31
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica del listado de asignaturas para el Docente. Este diseño asegura que los usuarios con rol `ROLE_DOCENTE` puedan visualizar y gestionar las asignaturas en las que tienen responsabilidades docentes.

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../images/diseño/verAsignaturas/secuencia.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/verAsignaturas/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente `AsignaturaList.tsx` que consume el endpoint `/api/asignaturas`.
- **AsignaturaController**: Endpoint `GET /api/asignaturas` protegido por `@PreAuthorize("hasRole('DOCENTE')")`.
- **AsignaturaService**: Lógica de negocio para la recuperación de asignaturas.
- **AsignaturaRepository**: Interface JPA para la persistencia de la entidad `Asignatura`.
- **AsignaturaDTO**: Objeto de transferencia para los datos de la asignatura (`id`, `titulo`, `codigo`, `cursoAcademico`).

## Decisiones de diseño

- **Entidad Asignatura**: Se implementará la entidad `Asignatura` con los campos `titulo`, `codigo` y `cursoAcademico` según el modelo del dominio.
- **Relaciones**: En esta fase de listado simple, se recuperan los datos básicos. En fases posteriores se incluirá la relación con `Grado` y `Profesor`.
- **Seguridad**: Acceso restringido a usuarios autenticados con rol docente.
