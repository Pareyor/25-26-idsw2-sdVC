# importarAlumnos() (Análisis)

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.1
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración para la importación específica de alumnos.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: importarAlumnos()](../../../modelosUML/analisis/importarAlumnos/colaboracion.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

### diagrama de secuencia
<div align=center>

|![Secuencia: importarAlumnos()](../../../modelosUML/analisis/importarAlumnos/secuencia.svg)|
|-|
|Código fuente: [secuencia.puml](secuencia.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### ImportarAlumnosView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Gestionar la selección del archivo de origen.
- Presentar resumen y posibles errores de importación.

**Colaboraciones**:
- **Entrada**: Docente.
- **Control**: `AlumnoController`.

### clases de control

#### AlumnoController
**Estereotipo**: Control  
**Responsabilidades**:
- Leer y procesar el archivo de alumnos.
- Validar duplicados y formatos de datos.
- Coordinar la persistencia masiva.

**Colaboraciones**:
- **Vista**: Responde a `ImportarAlumnosView`.
- **Repositorio**: `AlumnoRepository`.

### clases de entidad (entity)

#### AlumnoRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Persistencia de los datos de alumnos.

**Colaboraciones**:
- **Control**: Responde a `AlumnoController`.
