# Jorgestor > importarGrados > Análisis

> |[🏠️](/README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#importar-grados-docente)|**Análisis**|Diseño|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Fecha**: 2026-05-28
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `importarGrados()` mediante el patrón MVC. Este caso de uso permite la incorporación masiva de grados académicos al sistema desde un fichero externo, optimizando la carga inicial de datos y asegurando la consistencia del catálogo de grados.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: importarGrados()](../../../modelosUML/analisis/importarGrados/importarGrados-analisis.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### ImportarGradosView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Facilitar la carga de ficheros con información de grados.
- Informar sobre el progreso y resultado de la validación del archivo.
- Recoger la confirmación del docente para ejecutar la importación definitiva.

**Colaboraciones**:
- **Entrada**: `importarGrados()` desde `:GRADOS_ABIERTO`.
- **Control**: `GradoController`.
- **Salida**: Redirige a `VerGrados`.

### clases de control

#### GradoController
**Estereotipo**: Control  
**Responsabilidades**:
- Gestionar la lógica de lectura y validación de los datos de grados.
- Coordinar la persistencia masiva de las entidades válidas identificadas.

**Colaboraciones**:
- **Repositorio**: `GradoRepository`.

### clases de entidad (entity)

#### GradoRepository
**Estereotipo**: Entidad (Repositorio)  
**Responsabilidades**: Abstraer el almacenamiento persistente de los grados y permitir inserciones por lotes.

#### Grado
**Estereotipo**: Entidad  
**Responsabilidades**: Representar la unidad académica de Grado con sus atributos básicos (código, título).

## flujo de colaboración principal

### secuencia: importar grados

1. **Selección**: El docente inicia el proceso desde el listado de grados y carga un fichero.
2. **Procesamiento**: El controlador valida el contenido del archivo contra el esquema esperado.
3. **Revisión**: Se muestra una previsualización de los grados que serán creados.
4. **Ejecución**: Tras la confirmación, el repositorio persiste las nuevas entidades en el sistema.
5. **Cierre**: El sistema notifica la finalización y regresa a la vista general de grados.
