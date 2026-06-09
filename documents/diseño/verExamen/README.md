# Jorgestor > verExamen > Diseño

> |[🏠️](../../../README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#ver-examen-docente)|Análisis|[**Diseño**](README.md)|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Fecha**: 2026-06-09
- **Autor**: Gemini CLI

## propósito

Diseño técnico del caso de uso `verExamen()`, detallando la interacción entre los componentes frontend y backend, la gestión del estado de navegación contextual y la recuperación de datos detallados de exámenes asignados o borradores.

## diagramas de diseño

### diagrama de secuencia
<div align=center>

|![Diseño: verExamen()](../../../images/diseño/verExamen/secuencia.svg)|
|-|
|Código fuente: [secuencia.puml](secuencia.puml)|

</div>

## especificación técnica

### Frontend
- **Componente**: `DetalleExamen.tsx`.
- **Navegación**: Utiliza `useParams` para obtener el ID y `useLocation` para recuperar el estado de navegación (contexto de asignatura o procedencia).
- **Lógica**: Detecta mediante la URL si se está visualizando un examen asignado (`/detalle/:id`) o un borrador (`/detalle-borrador/:id`) para invocar el endpoint correspondiente en `ExamenService`.
- **UI**: Renderiza una tabla comparativa con las preguntas, respuestas del alumno y la respuesta correcta.

### Backend
- **Controlador**: `ExamenController` expone los endpoints `GET /api/examenes/detalle/{id}` y `GET /api/examenes/detalle-borrador/{id}`.
- **Servicio**: `ExamenService` implementa la lógica de recuperación, conversión a `DetalleExamenDTO` y validación de permisos del docente sobre el recurso.
- **Seguridad**: Se aplica `@PreAuthorize("hasAuthority('ROLE_DOCENTE')")` para restringir el acceso.
