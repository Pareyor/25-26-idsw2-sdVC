# asignarExamenes() (Análisis)

## información del artefacto

- **Proyecto**: IdSw1-SdR / VC
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.1
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `asignarExamenes()` mediante el patrón MVC. Este proceso permite al docente vincular los exámenes previamente generados con los alumnos correspondientes.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: asignarExamenes()](../../../modelosUML/analisis/asignarExamenes/colaboracion.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

### diagrama de secuencia
<div align=center>

|![Secuencia: asignarExamenes()](../../../modelosUML/analisis/asignarExamenes/secuencia.svg)|
|-|
|Código fuente: [secuencia.puml](secuencia.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### AsignarExamenesView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Mostrar la lista de alumnos pendientes de asignación.
- Permitir la búsqueda y selección de alumnos.
- Recibir la confirmación de asignación.

**Colaboraciones**:
- **Entrada**: Docente.
- **Control**: `ExamenController`.

### clases de control

#### ExamenController
**Estereotipo**: Control  
**Responsabilidades**:
- Recuperar los alumnos matriculados.
- Generar las claves alfanuméricas de acceso para cada examen.
- Vincular las instancias de examen con los alumnos seleccionados.

**Colaboraciones**:
- **Vista**: Responde a `AsignarExamenesView`.
- **Repositorio**: `AlumnoRepository`, `ExamenRepository`.

### clases de entidad (entity)

#### AlumnoRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Proporcionar acceso a los datos de los alumnos y sus vinculaciones.

**Colaboraciones**:
- **Control**: Responde a `ExamenController`.
