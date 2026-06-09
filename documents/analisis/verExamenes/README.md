# Jorgestor > verExamenes > Análisis

> |[🏠️](../../../README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#corregir-examenes-docente)|**Análisis**|Diseño|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Fecha**: 2026-06-09
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `verExamenes()` mediante el patrón MVC, identificando las clases de análisis y sus responsabilidades para visualizar el panel de corrección de exámenes, permitiendo el filtrado, la corrección masiva (opcional por asignatura) y el acceso al detalle de cada examen.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: verExamenes()](../../../images/analisis/verExamenes/colaboracion.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### CorregirExamenesList
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Presentar el panel de gestión y corrección de exámenes.
- Aplicar filtros por estado (Asignado/Corregido) y asignatura (si es contextual).
- Permitir la corrección masiva de exámenes.
- Agrupar los exámenes por tipo para mejorar la legibilidad.

**Colaboraciones**:
- **Control**: Se comunica con `ExamenController`.

### clases de control

#### ExamenController
**Estereotipo**: Control  
**Responsabilidades**:
- Coordinar la obtención de exámenes pendientes de corrección.
- Gestionar las solicitudes de corrección masiva.
- Servir de puente entre la vista y el servicio de exámenes.

**Colaboraciones**:
- **Vista**: Responde a `CorregirExamenesList`.
- **Servicio**: Delega en `ExamenService`.

### clases de entidad (entity)

#### ExamenService
**Estereotipo**: Entidad/Servicio  
**Responsabilidades**:
- Recuperar exámenes filtrados según docente, asignatura o alumno.
- Ejecutar la lógica de corrección automática.

**Colaboraciones**:
- **Control**: Responde a `ExamenController`.
- **Repositorio**: Accede a `ExamenRepository`.

#### Examen
**Estereotipo**: Entidad  
**Responsabilidades**:
- Almacenar los datos del examen (alumno, asignatura, estado, nota).

## flujo de colaboración principal

1. **Inicio**: El Docente accede al panel de corrección desde el contexto de asignatura o directamente (si existiera enlace).
2. **Consulta**: `CorregirExamenesList` solicita el listado de exámenes al `ExamenController`.
3. **Recuperación**: `ExamenController` solicita los datos al `ExamenService`.
4. **Respuesta**: Los exámenes fluyen de vuelta a la vista agrupados por tipo.
5. **Corrección**: El Docente puede activar la corrección masiva mediante `ExamenController`.
6. **Visualización**: La vista renderiza el panel con los estados actualizados de los exámenes.
