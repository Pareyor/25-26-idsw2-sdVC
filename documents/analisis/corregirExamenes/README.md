# corregirExamenes() (Análisis)

## información del artefacto

- **Proyecto**: IdSw1-SdR / VC
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `corregirExamenes()` mediante el patrón MVC, identificando las clases de análisis, sus responsabilidades y colaboraciones necesarias para el proceso de corrección de exámenes.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: corregirExamenes()](../../../modelosUML/analisis/corregirExamenes/colaboracion.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

### diagrama de secuencia
<div align=center>

|![Secuencia: corregirExamenes()](../../../modelosUML/analisis/corregirExamenes/secuencia.svg)|
|-|
|Código fuente: [secuencia.puml](secuencia.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### CorregirExamenesView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Recibir la solicitud de corrección de exámenes.
- **Cargar:** Presentar la interfaz para la carga de los exámenes realizados (archivos/datos).
- **Corregir:** Permitir al docente solicitar la ejecución de la corrección de los exámenes cargados.
- **Cancelar:** Gestionar la interrupción voluntaria del proceso, volviendo al estado anterior.
- Mostrar el resultado detallado de la corrección (éxito o errores de detección).

**Colaboraciones**:
- **Entrada**: Recibe solicitud del Docente.
- **Control**: Se comunica con `ExamenController` para cargar y procesar.
- **Salida**: Navega a `EXAMENES_CORREGIDOS` tras éxito o a `CompletarGestion` tras cancelación.

### clases de control

#### ExamenController
**Estereotipo**: Control  
**Responsabilidades**:
- **Gestionar Carga:** Validar el formato y la integridad de los exámenes cargados antes de su procesamiento.
- **Coordinar Corrección:** Orquestar el flujo de corrección comparando los exámenes con las plantillas de respuesta.
- **Gestionar Estado:** Asegurar que el sistema vuelva a un estado consistente si el docente cancela la operación.

**Colaboraciones**:
- **Vista**: Responde a solicitudes de `CorregirExamenesView`.
- **Repositorio**: Delega la persistencia y consulta a `ExamenRepository`.

### clases de entidad (entity)

#### ExamenRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Gestionar el almacenamiento y recuperación de datos de exámenes.
- Procesar la persistencia de las correcciones realizadas.

**Colaboraciones**:
- **Control**: Responde a solicitudes de `ExamenController`.
