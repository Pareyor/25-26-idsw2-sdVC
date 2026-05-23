# importarAlumnos() (Análisis)

## información del artefacto

- **Proyecto**: IdSw1-SdR / VC
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
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
- Gestionar la subida de archivos de alumnos.
- Mostrar previsualización o errores de validación de formato.

### clases de control

#### AlumnoController
**Estereotipo**: Control  
**Responsabilidades**:
- Orquestar el proceso de importación de alumnos.
- Validar reglas de negocio (ej: alumnos duplicados).

### clases de entidad (entity)

#### Importador
**Estereotipo**: Entidad  
**Responsabilidades**:
- Parsear datos de alumnos de fuentes externas.

#### AlumnoRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Persistir los nuevos registros de alumnos.
