# Jorgestor > eliminarRespuesta > Análisis

> |[🏠️](/README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#eliminar-respuesta-docente)|**Análisis**|Diseño|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Fecha**: 2026-05-28
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `eliminarRespuesta()` mediante el patrón MVC. Este caso de uso permite la remoción permanente de una opción de respuesta del sistema, requiriendo confirmación explícita por parte del docente debido a la irreversibilidad de la acción.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: eliminarRespuesta()](../../../images/analisis/eliminarRespuesta/eliminarRespuesta-analisis.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### EliminarRespuestaView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Presentar la advertencia de eliminación irreversible.
- Mostrar los datos de la respuesta que se va a eliminar para evitar errores.
- Capturar la confirmación o cancelación de la acción.

**Colaboraciones**:
- **Entrada**: `eliminarRespuesta(id)` desde `:RESPUESTAS_ABIERTO`, `:RESPUESTA_ABIERTO`, `:RESPUESTAS_CONTEXTUALES_ABIERTO`, `:RESPUESTA_CONTEXTUAL_ABIERTO` o `:EditarRespuestaView`.
- **Control**: `PreguntaController`.
- **Salida**: Redirige a `verRespuestas()` tras confirmar o cancelar.

### clases de control

#### PreguntaController
**Estereotipo**: Control  
**Responsabilidades**:
- Coordinar la eliminación de la respuesta en la capa de persistencia.

**Colaboraciones**:
- **Repositorio**: `RespuestaRepository`.

### clases de entidad (entity)

#### RespuestaRepository
**Estereotipo**: Entidad (Repositorio)  
**Responsabilidades**: Proveer el método de eliminación por identificador.

#### Respuesta
**Estereotipo**: Entidad  
**Responsabilidades**: Representar la entidad a eliminar.

## flujo de colaboración principal

### secuencia: eliminar respuesta

1. **Selección**: El docente solicita eliminar una respuesta específica.
2. **Confirmación**: Se muestra la vista de confirmación con la advertencia legal.
3. **Ejecución**: Al confirmar, el controlador ordena al repositorio la eliminación física del registro.
4. **Finalización**: El sistema redirige automáticamente al listado de respuestas actualizado.
