# generarExamenes() (Análisis)

## información del artefacto

- **Proyecto**: IdSw1-SdR / VC
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `generarExamenes()` mediante el patrón MVC, identificando las clases de análisis, sus responsabilidades y colaboraciones necesarias para la generación algorítmica de exámenes.

## diagrama de colaboración

<div align=center>

|![Análisis: generarExamenes()](../../../modelosUML/analisis/generarExamenes/colaboracion.svg)|
|-|
|Código fuente: [colaboracion.puml](../../../modelosUML/analisis/generarExamenes/colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### GenerarExamenesView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Recibir la solicitud de generación de exámenes.
- Presentar la interfaz para introducir parámetros (Asignatura, Temas, etc.).
- Mostrar progreso y resultados de la generación.
- Gestionar cancelación de la generación.

**Colaboraciones**:
- **Entrada**: Recibe solicitud del Docente.
- **Control**: Se comunica con `GeneracionController`.
- **Salida**: Navega a `EXAMENES_GENERADOS` tras éxito.

### clases de control

#### GeneracionController
**Estereotipo**: Control  
**Responsabilidades**:
- Coordinar el proceso completo de generación.
- Delegar validación de datos mínimos a `Validador`.
- Coordinar la ejecución del algoritmo de generación.

**Colaboraciones**:
- **Vista**: Responde a `GenerarExamenesView`.
- **Validador**: Delega validaciones.
- **Generador**: Delega generación algorítmica a `ExamenGenerator`.

### clases de entidad (entity)

#### ExamenGenerator
**Estereotipo**: Entidad  
**Responsabilidades**:
- Encapsular la lógica del algoritmo de generación de exámenes.
- Seleccionar preguntas basadas en dificultad y temas.
- Generar los diferentes tipos de examen solicitados.

**Colaboraciones**:
- **Control**: Responde a `GeneracionController`.
- **Repositorio**: Solicita datos a `ExamenRepository`.

#### ExamenRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Proporcionar acceso a bancos de preguntas, asignaturas y temas.
- Persistir los exámenes generados.

**Colaboraciones**:
- **Generador**: Suministra datos maestros.

#### Validador
**Estereotipo**: Entidad  
**Responsabilidades**:
- Verificar que se han introducido todos los datos obligatorios.
- Validar que existan suficientes preguntas para los parámetros solicitados.
