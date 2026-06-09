# Jorgestor > editarRespuesta > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Análisis](/documents/analisis/editarRespuesta/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0
- **Fecha**: 2026-06-05
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica para la modificación de una respuesta existente por parte del Docente. Se aplica el patrón "El Gordo", permitiendo la actualización de todos los campos de la respuesta en una sola operación.

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../images/diseño/editarRespuesta/secuencia.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/editarRespuesta/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente `RespuestaEditComponent` que gestiona el formulario de edición.
- **RespuestaController**: Endpoint `PUT /api/respuestas/{id}` protegido por `@PreAuthorize("hasAuthority('ROLE_DOCENTE')")`.
- **RespuestaService**: Lógica para actualizar la respuesta.
- **PreguntaService**: Servicio para validar que la respuesta pertenece a una pregunta vinculada al docente.
- **RespuestaRepository**: Interface para acceder a la base de datos de respuestas.
- **RespuestaDTO**: Clase para transferir los datos de la respuesta.

## Decisiones de diseño

- **Seguridad**: El acceso está restringido a usuarios con `ROLE_DOCENTE`. Se valida la autoría del docente sobre la pregunta padre.
- **Integridad**: No se permite cambiar la pregunta padre de una respuesta (la relación es inmutable en este caso de uso).
- **Arquitectura**: Patrón Service-to-Service para validación de autoría.
- **Edición**: Se aplica el patrón "El Gordo" actualizando todos los campos (contenido y estado de correctitud) en un solo PUT.
