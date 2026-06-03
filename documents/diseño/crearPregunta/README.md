# Jorgestor > crearPregunta > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Análisis](/documents/analisis/crearPregunta/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0
- **Fecha**: 2026-06-03
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica de la creación de preguntas por parte del Docente dentro de una asignatura. Se aplica el patrón "El Delgado" para una creación rápida y vinculación con la Batería de Preguntas de la asignatura.

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../modelosUML/diseño/crearPregunta/secuencia.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/crearPregunta/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente `PreguntaCreate.tsx` que gestiona el formulario de alta y la selección de la asignatura.
- **PreguntaController**: Endpoint `POST /api/preguntas` protegido por `@PreAuthorize("hasRole('DOCENTE')")`.
- **PreguntaService**: Lógica de negocio para validar la existencia de la asignatura y persistir la nueva pregunta en la batería correspondiente.
- **PreguntaRepository**: Interface para la persistencia en base de datos de las preguntas.
- **AsignaturaRepository**: Interface para validar la existencia de la asignatura y acceder a su batería de preguntas.
- **PreguntaDTO**: Estructura de datos para la transferencia desde la vista.

## Decisiones de diseño

- **Contextualización**: La pregunta se crea vinculada a una asignatura específica, lo que permite identificar automáticamente la `BateriaDePreguntas` de destino.
- **Seguridad**: Solo usuarios con el rol `ROLE_DOCENTE` pueden crear preguntas.
- **Flujo de Usuario**: Tras la creación, el sistema redirige al listado de preguntas (`PreguntaList`) con un mensaje de éxito.
- **Patrón de Creación**: Se utiliza el patrón "El Delgado", permitiendo la creación desde el listado y retornando a él tras completar la acción.
