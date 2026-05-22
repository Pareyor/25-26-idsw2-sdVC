# importarPreguntas() (Análisis)

## información del artefacto

- **Proyecto**: IdSw1-SdR / VC
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `importarPreguntas()` mediante el patrón MVC, identificando las clases de análisis para la importación masiva de preguntas desde archivos externos hacia la batería de preguntas.

## diagrama de colaboración

<div align=center>

|![Análisis: importarPreguntas()](../../../modelosUML/analisis/importarPreguntas/colaboracion.svg)|
|-|
|Código fuente: [colaboracion.puml](../../../modelosUML/analisis/importarPreguntas/colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### ImportarPreguntasView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Presentar la interfaz para la selección del archivo de preguntas.
- Capturar la entrada del docente (archivo de preguntas).
- Mostrar estados de progreso y resultados de la importación (éxito/error).
- Permitir la cancelación del proceso.

**Colaboraciones**:
- **Entrada**: Docente solicita importación.
- **Control**: Se comunica con `PreguntaController`.
- **Salida**: Navega a los estados de listado de preguntas tras finalizar.

### clases de control

#### PreguntaController
**Estereotipo**: Control  
**Responsabilidades**:
- Coordinar el flujo de importación.
- Solicitar el parseo del archivo al importador.
- Validar la lógica de negocio (ej. evitar duplicados si aplica).
- Ordenar la persistencia de las nuevas preguntas.

**Colaboraciones**:
- **Vista**: Recibe solicitudes de `ImportarPreguntasView`.
- **Entidad**: Utiliza `ImportadorPreguntas` y `PreguntaRepository`.

### clases de entidad (entity)

#### ImportadorPreguntas
**Estereotipo**: Entidad  
**Responsabilidades**:
- Interpretar el formato del archivo (CSV, JSON, etc.).
- Extraer y transformar los datos a objetos de dominio `Pregunta` y `Respuesta`.

#### PreguntaRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Gestionar la persistencia masiva de preguntas en el sistema.
