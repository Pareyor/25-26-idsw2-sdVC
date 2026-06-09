# Jorgestor > verExamen > Análisis

> |[🏠️](../../../README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#ver-examen-docente)|**Análisis**|Diseño|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Fecha**: 2026-06-09
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `verExamen()` mediante el patrón MVC, identificando las clases de análisis y sus responsabilidades para visualizar el contenido detallado de un examen corregido, incluyendo preguntas, respuestas del alumno y resultados.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: verExamen()](../../../images/analisis/verExamen/colaboracion.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### ExamenDetallePage
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Presentar el detalle completo de un examen (preguntas, respuestas alumno, correctas, resultados).
- Mostrar la nota final si el examen está corregido.
- Permitir la navegación de retorno a la gestión previa.

**Colaboraciones**:
- **Control**: Se comunica con `ExamenController`.

### clases de control

#### ExamenController
**Estereotipo**: Control  
**Responsabilidades**:
- Coordinar la recuperación del detalle de un examen específico.
- Coordinar la recuperación del detalle de un borrador de examen.
- Servir de puente entre la vista y el servicio de exámenes.

**Colaboraciones**:
- **Vista**: Responde a `ExamenDetallePage`.
- **Servicio**: Delega en `ExamenService`.

### clases de entidad (entity)

#### ExamenService
**Estereotipo**: Entidad/Servicio  
**Responsabilidades**:
- Obtener el detalle de un examen (corregido o no).
- Obtener el detalle de un borrador de examen.
- Validar permisos del docente sobre el examen solicitado.

**Colaboraciones**:
- **Control**: Responde a `ExamenController`.
- **Repositorio**: Accede a `ExamenRepository` y `ExamenBorradorRepository`.

#### Examen
**Estereotipo**: Entidad  
**Responsabilidades**:
- Almacenar los datos del examen (alumno, asignatura, estado, nota).

#### Pregunta
**Estereotipo**: Entidad  
**Responsabilidades**:
- Almacenar el contenido de las preguntas y sus opciones de respuesta.

## flujo de colaboración principal

1. **Inicio**: El Docente selecciona "Ver Detalle" desde una lista de exámenes (asignados o borradores).
2. **Consulta**: `ExamenDetallePage` solicita el detalle al `ExamenController` usando el ID del examen.
3. **Recuperación**: `ExamenController` solicita los datos al `ExamenService`.
4. **Validación**: `ExamenService` verifica la existencia del examen/borrador y los permisos del docente.
5. **Respuesta**: Los datos del examen y las preguntas asociadas fluyen de vuelta hasta la vista.
6. **Visualización**: La vista renderiza la información detallada con el desglose de preguntas y resultados.
7. **Navegación**: El Docente puede regresar a la vista de gestión previa.
