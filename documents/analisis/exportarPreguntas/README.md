# exportarPreguntas() (Análisis)

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso abstracto `exportarPreguntas()` mediante el patrón MVC. Este caso de uso es invocado por procesos de exportación global para gestionar la salida de datos de la batería de preguntas.

## diagrama de colaboración

<div align=center>

|![Análisis: exportarPreguntas()](../../../modelosUML/analisis/exportarPreguntas/colaboracion.svg)|
|-|
|Código fuente: [colaboracion.puml](../../../modelosUML/analisis/exportarPreguntas/colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### ExportarPreguntasView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Presentar las opciones de exportación para la batería de preguntas.
- Informar sobre el estado de la generación del archivo.
- Facilitar la descarga del archivo al docente.

**Colaboraciones**:
- **Entrada**: Invocado por `exportarConfiguracionGlobal`.
- **Control**: Se comunica con `PreguntaController`.

### clases de control

#### PreguntaController
**Estereotipo**: Control  
**Responsabilidades**:
- Coordinar la obtención de las preguntas y sus respuestas asociadas.
- Solicitar la generación del archivo de exportación.

**Colaboraciones**:
- **Vista**: Recibe solicitudes de `ExportarPreguntasView`.
- **Entidad**: Utiliza `Exportador` y `PreguntaRepository`.

### clases de entidad (entity)

#### Exportador
**Estereotipo**: Entidad  
**Responsabilidades**:
- Aplicar el formato de transformación (CSV, XML, etc.) a los datos de las preguntas.

#### PreguntaRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Gestionar el acceso a los datos almacenados de las preguntas.
