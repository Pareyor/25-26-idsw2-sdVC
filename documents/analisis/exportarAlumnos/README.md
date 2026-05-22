# exportarAlumnos() (Análisis)

## información del artefacto

- **Proyecto**: IdSw1-SdR / VC
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso abstracto `exportarAlumnos()` mediante el patrón MVC. Este caso de uso es invocado por procesos de exportación global para gestionar la salida de datos de los alumnos.

## diagrama de colaboración

<div align=center>

|![Análisis: exportarAlumnos()](../../../modelosUML/analisis/exportarAlumnos/colaboracion.svg)|
|-|
|Código fuente: [colaboracion.puml](../../../modelosUML/analisis/exportarAlumnos/colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### ExportarAlumnosView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Presentar las opciones de exportación específicas para alumnos (ej. selección de filtros o formatos).
- Informar sobre el estado de la generación del archivo.
- Entregar el archivo resultante al usuario.

**Colaboraciones**:
- **Entrada**: Invocado por `exportarConfiguracionGlobal`.
- **Control**: Se comunica con `AlumnoController`.

### clases de control

#### AlumnoController
**Estereotipo**: Control  
**Responsabilidades**:
- Orquestar la recuperación de datos de alumnos.
- Coordinar con el exportador para la generación del documento.

**Colaboraciones**:
- **Vista**: Recibe solicitudes de `ExportarAlumnosView`.
- **Entidad**: Utiliza `Exportador` y `AlumnoRepository`.

### clases de entidad (entity)

#### Exportador
**Estereotipo**: Entidad  
**Responsabilidades**:
- Transformar los datos de los alumnos al formato de salida requerido.

#### AlumnoRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Proveer el acceso a la lista completa o filtrada de alumnos registrados.
