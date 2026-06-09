# Jorgestor > verExamenes > Diseño

> |[🏠️](../../../README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#corregir-examenes-docente)|Análisis|[**Diseño**](README.md)|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Fecha**: 2026-06-09
- **Autor**: Gemini CLI

## propósito

Diseño técnico del caso de uso `verExamenes()`, detallando la interacción entre los componentes frontend y backend para la visualización del panel de corrección de exámenes, incluyendo la agrupación por tipo y el filtrado por asignatura cuando corresponde.

## diagramas de diseño

### diagrama de secuencia
<div align=center>

|![Diseño: verExamenes()](../../../images/diseño/verExamenes/secuencia.svg)|
|-|
|Código fuente: [secuencia.puml](secuencia.puml)|

</div>

## especificación técnica

### Frontend
- **Componente**: `CorregirExamenesList.tsx`.
- **Navegación**: Utiliza `useLocation` para recuperar el estado de navegación (asignatura context) y `useNavigate` para redirigir a detalle o volver.
- **Lógica**: Solicita el listado de exámenes y los agrupa en el cliente por tipo de examen (`tipo`) para su visualización.
- **UI**: Renderiza un panel con filtros por estado y acordeones para cada tipo de examen.

### Backend
- **Controlador**: `ExamenController` expone el endpoint `GET /api/examenes/corregir/listar`.
- **Servicio**: `ExamenService` implementa la lógica de recuperación de exámenes filtrados por el docente.
- **Seguridad**: Se aplica `@PreAuthorize("hasAuthority('ROLE_DOCENTE')")` para restringir el acceso.
