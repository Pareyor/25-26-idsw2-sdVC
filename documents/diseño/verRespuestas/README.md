# Jorgestor > verRespuestas > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Análisis](/documents/analisis/verRespuestas/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.1
- **Fecha**: 2026-06-05
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica para la visualización de las respuestas asociadas a una pregunta específica, incluyendo validación de autoría.

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../images/diseño/verRespuestas/secuencia.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/verRespuestas/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente `RespuestasListComponent` que gestiona la visualización y filtrado.
- **RespuestaController**: Endpoint `GET /api/respuestas/pregunta/{preguntaId}` protegido por `@PreAuthorize("hasAuthority('ROLE_DOCENTE')")`.
- **RespuestaService**: Lógica para recuperar y filtrar respuestas.
- **PreguntaService**: Servicio para validar que la pregunta existe y pertenece al docente.
- **RespuestaRepository**: Interface para acceder a la base de datos de respuestas.
- **RespuestaDTO**: Clase para transferir la información de las respuestas a la vista.

## Decisiones de diseño

- **Seguridad**: El acceso está restringido a usuarios con `ROLE_DOCENTE`. Se valida la autoría del docente sobre la pregunta antes de devolver las respuestas.
- **Relación**: El listado de respuestas es estrictamente dependiente de una pregunta.
- **Arquitectura**: Se aplica el patrón Service-to-Service para la validación de pertenencia.
- **Filtrado**: Se implementa filtrado por contenido en el backend para optimizar el manejo de grandes volúmenes de datos.
