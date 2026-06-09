# Jorgestor > editarRespuesta > Análisis

> |[🏠️](/README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#editar-respuesta-docente)|**Análisis**|Diseño|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Fecha**: 2026-05-28
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `editarRespuesta()` mediante el patrón MVC. Este caso de uso sigue el patrón **"El Gordo"**, permitiendo la edición integral de los datos de una opción de respuesta (contenido y veracidad), así como el acceso a su eliminación o el retorno al listado de respuestas.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: editarRespuesta()](../../../images/analisis/editarRespuesta/editarRespuesta-analisis.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### EditarRespuestaView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Mostrar el formulario de edición con los datos actuales de la respuesta.
- Capturar las modificaciones del usuario (contenido, estado de corrección).
- Validar preliminarmente los datos antes de enviarlos al controlador.
- Facilitar la navegación a la eliminación de la respuesta o la vuelta al listado.

**Colaboraciones**:
- **Entrada**: `editarRespuesta(id)` desde `:RESPUESTAS_ABIERTO`, `:RESPUESTA_ABIERTO`, `:RESPUESTAS_CONTEXTUALES_ABIERTO`, `:RESPUESTA_CONTEXTUAL_ABIERTO` o tras una creación.
- **Control**: `PreguntaController`.
- **Salida**: Redirige a `verRespuestas()` o `eliminarRespuesta()`.

### clases de control

#### PreguntaController
**Estereotipo**: Control  
**Responsabilidades**:
- Gestionar la carga y actualización de respuestas asociadas a las preguntas.
- Coordinar la lógica de negocio para asegurar que los cambios sean persistidos correctamente.

**Colaboraciones**:
- **Repositorio**: `RespuestaRepository`.

### clases de entidad (entity)

#### RespuestaRepository
**Estereotipo**: Entidad (Repositorio)  
**Responsabilidades**: Proveer métodos para buscar y actualizar entidades `Respuesta` en la persistencia.

#### Respuesta
**Estereotipo**: Entidad  
**Responsabilidades**: Mantener el estado de una opción de respuesta.

## flujo de colaboración principal

### secuencia: editar respuesta

1. **Carga**: La vista solicita al controlador los datos de la respuesta mediante su ID.
2. **Recuperación**: El controlador obtiene la entidad desde el repositorio.
3. **Edición**: El usuario modifica los campos en la vista.
4. **Actualización**: Al pulsar "Guardar", la vista envía los datos al controlador, que invoca el método de actualización en el repositorio.
5. **Confirmación**: El sistema informa del éxito y permite continuar editando o volver atrás.
