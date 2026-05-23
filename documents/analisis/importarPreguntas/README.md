# importarPreguntas() (Análisis)

## información del artefacto

- **Proyecto**: IdSw1-SdR / VC
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.1
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración para la importación masiva de preguntas desde archivos externos.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: importarPreguntas()](../../../modelosUML/analisis/importarPreguntas/colaboracion.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

### diagrama de secuencia
<div align=center>

|![Secuencia: importarPreguntas()](../../../modelosUML/analisis/importarPreguntas/secuencia.svg)|
|-|
|Código fuente: [secuencia.puml](secuencia.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### ImportarPreguntasView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Capturar el archivo de preguntas y la asignatura de destino.
- Informar sobre el progreso y resultado de la importación.

**Colaboraciones**:
- **Entrada**: Docente.
- **Control**: `PreguntaController`.

### clases de control

#### PreguntaController
**Estereotipo**: Control  
**Responsabilidades**:
- Procesar la lectura del archivo.
- Validar la integridad de cada pregunta importada.
- Coordinar la inserción en la batería de preguntas.

**Colaboraciones**:
- **Vista**: Responde a `ImportarPreguntasView`.
- **Repositorio**: `PreguntaRepository`.

### clases de entidad (entity)

#### PreguntaRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Persistencia de las nuevas preguntas.

**Colaboraciones**:
- **Control**: Responde a `PreguntaController`.
