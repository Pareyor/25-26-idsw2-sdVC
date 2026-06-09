# Jorgestor > eliminarRespuesta > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Análisis](/documents/analisis/eliminarRespuesta/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0
- **Fecha**: 2026-06-05
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica para la eliminación de una respuesta existente por parte del Docente.

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../images/diseño/eliminarRespuesta/eliminarRespuesta.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/eliminarRespuesta/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente que maneja la eliminación y refresco de la vista.
- **RespuestaController**: Endpoint `DELETE /api/respuestas/{id}` protegido por `@PreAuthorize("hasAuthority('ROLE_DOCENTE')")`.
- **RespuestaService**: Lógica para eliminar la respuesta tras validar la propiedad.
- **PreguntaService**: Servicio para validar que la respuesta pertenece a una pregunta del docente.
- **RespuestaRepository**: Interface para acceder a la base de datos de respuestas.

## Decisiones de diseño

- **Seguridad**: El acceso está restringido a usuarios con `ROLE_DOCENTE`. Se valida la autoría del docente sobre la pregunta padre.
- **Integridad**: Tras la eliminación, se asegura la coherencia del estado de la pregunta.
- **Arquitectura**: Patrón Service-to-Service para validación de autoría.
- **Interacción**: Se requiere confirmación en el frontend antes de realizar la petición DELETE.
