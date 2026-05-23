# asignarExamenes() (Análisis)

## información del artefacto

- **Proyecto**: IdSw1-SdR / VC
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
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
- Presentar la lista de exámenes generados pendientes de asignación.
- **Búsqueda:** Facilitar la búsqueda y filtrado de alumnos (por nombre/DNI) para cada grupo/grado.
- Permitir la selección masiva ("Seleccionar todos") o manual de destinatarios.
- Mostrar confirmación de la asignación realizada e informar sobre la generación de claves.
- Permitir cancelar el proceso de asignación.

**Colaboraciones**:
- **Entrada**: Docente inicia desde `EXAMENES_GENERADOS`.
- **Control**: Se comunica con `AsignacionController`.
- **Salida**: Navega a `EXAMENES_ASIGNADOS` tras éxito.

### clases de control

#### AsignacionController
**Estereotipo**: Control  
**Responsabilidades**:
- **Gestionar Búsqueda:** Procesar criterios de búsqueda de alumnos.
- **Gestionar Asignación:** Gestionar la lógica de emparejamiento entre exámenes y alumnos.
- **Generación de Claves:** Generar claves alfanuméricas únicas para cada instancia de examen asignada.
- Ordenar la actualización de los registros de exámenes con su asignatario y clave.

**Colaboraciones**:
- **Vista**: Recibe solicitudes de `AsignarExamenesView`.
- **Entidad**: Utiliza `ExamenRepository` y `AlumnoRepository`.

### clases de entidad (entity)

#### ExamenRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Actualizar el estado de los exámenes para incluir la referencia al alumno asignado.

#### AlumnoRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Proveer la lista de alumnos disponibles para la asignación (filtrados por grado/asignatura).
