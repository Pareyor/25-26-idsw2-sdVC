# crearPregunta() (Análisis)

## información del artefacto

- **Proyecto**: IdSw1-SdR / VC
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `crearPregunta()` mediante el patrón MVC. Este caso de uso aplica la filosofía de "creación rápida" con datos mínimos y transferencia inmediata a la edición detallada.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: crearPregunta()](../../../modelosUML/analisis/crearPregunta/colaboracion.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

### diagrama de secuencia
<div align=center>

|![Secuencia: crearPregunta()](../../../modelosUML/analisis/crearPregunta/secuencia.svg)|
|-|
|Código fuente: [secuencia.puml](secuencia.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### CrearPreguntaView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Presentar el formulario de creación con campos obligatorios (Asignatura, Enunciado, Tema, Dificultad).
- Validar preliminarmente la completitud de los datos en el cliente.
- Redirigir al usuario a la vista de edición tras la creación exitosa.
- Permitir la cancelación de la operación.

**Colaboraciones**:
- **Entrada**: Docente inicia desde el listado de preguntas.
- **Control**: Se comunica con `PreguntaController`.
- **Salida**: Navega a `PREGUNTA_ABIERTO` (edición) o vuelve al listado.

### clases de control

#### PreguntaController
**Estereotipo**: Control  
**Responsabilidades**:
- Gestionar el ciclo de vida de la creación de la pregunta.
- Asegurar que se cumplan las precondiciones de negocio para la creación.

**Colaboraciones**:
- **Vista**: Recibe solicitudes de `CrearPreguntaView`.
- **Entidad**: Utiliza `PreguntaRepository`.

### clases de entidad (entity)

#### PreguntaRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Persistir la nueva instancia de la pregunta en la base de datos con los datos mínimos proporcionados.
- Devolver el objeto creado (o su ID) para permitir la navegación subsiguiente.
