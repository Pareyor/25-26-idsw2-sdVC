# Jorgestor > cancelarGeneracion > Análisis

> |[🏠️](/README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#cancelar-generación-docente)|**Análisis**|Diseño|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Fecha**: 2026-05-28
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `cancelarGeneracion()` mediante el patrón MVC. Este caso de uso permite interrumpir el proceso de generación de exámenes tras la vista previa, asegurando la eliminación de los datos temporales creados y el retorno correcto al estado anterior (Global o Contextual).

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: cancelarGeneracion()](../../../images/analisis/cancelarGeneracion/cancelarGeneracion.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### CancelarGeneracionView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Presentar la advertencia de pérdida de datos de los exámenes generados.
- Capturar la confirmación del usuario para proceder con la cancelación.
- Redirigir al flujo correspondiente (Menú principal o Edición de asignatura).

**Colaboraciones**:
- **Entrada**: `cancelarGeneracion()` desde `:EXAMENES_GENERADOS`, `:EXAMENES_GENERADOS_CONTEXTUALES` o desde la colaboración de generación.
- **Control**: `ExamenController`.
- **Salida**: Redirige a `CompletarGestion` (Global) o `EditarAsignatura` (Contextual).

### clases de control

#### ExamenController
**Estereotipo**: Control  
**Responsabilidades**:
- Gestionar la lógica de interrupción del proceso de generación.
- Solicitar la limpieza de los exámenes que se crearon en memoria o estado temporal.

**Colaboraciones**:
- **Repositorio**: `ExamenRepository`.

### clases de entidad (entity)

#### ExamenRepository
**Estereotipo**: Entidad (Repositorio)  
**Responsabilidades**: Abstraer la persistencia de los exámenes, incluyendo la eliminación de registros temporales.

#### Examen
**Estereotipo**: Entidad  
**Responsabilidades**: Representar los exámenes generados que van a ser descartados.

## flujo de colaboración principal

### secuencia: cancelar generación

1. **Solicitud**: El docente pulsa "Cancelar" desde la vista previa de exámenes.
2. **Advertencia**: Se muestra la vista de confirmación informando de la pérdida de los exámenes generados.
3. **Limpieza**: Al confirmar, el controlador ordena al repositorio eliminar los exámenes temporales.
4. **Retorno**: El sistema detecta si la generación era contextual o global y redirige al punto de origen adecuado.
