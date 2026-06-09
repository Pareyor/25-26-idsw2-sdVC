# Jorgestor > crearRespuesta > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Análisis](/documents/analisis/crearRespuesta/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0
- **Fecha**: 2026-06-05
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica para la creación de respuestas asociadas a una pregunta específica por parte del Docente, con validación de propiedad.

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../images/diseño/crearRespuesta/secuencia.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/crearRespuesta/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente `RespuestaCreateComponent` que envía los datos de la nueva respuesta.
- **RespuestaController**: Endpoint `POST /api/respuestas` protegido por `@PreAuthorize("hasAuthority('ROLE_DOCENTE')")`.
- **RespuestaService**: Lógica para persistir la respuesta.
- **PreguntaService**: Servicio para validar que la pregunta existe y pertenece al docente autenticado.
- **RespuestaRepository**: Interface para acceder a la base de datos de respuestas.
- **RespuestaDTO**: Clase para transferir los datos de la respuesta.

## Decisiones de diseño

- **Seguridad**: El acceso está restringido a usuarios con `ROLE_DOCENTE`. Se valida la autoría del docente sobre la pregunta padre.
- **Integridad**: La respuesta es un componente de composición de una pregunta, por lo que requiere un `preguntaId` válido.
- **Arquitectura**: Se aplica el patrón Service-to-Service para la validación de pertenencia.
