# Jorgestor > exportarAsignaturas > Análisis

> |[🏠️](/README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#exportar-asignaturas-docente)|**Análisis**|Diseño|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Fecha**: 2026-05-28
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso abstracto `exportarAsignaturas()` mediante el patrón MVC. Este caso de uso es invocado por el proceso de exportación global para gestionar la salida de datos del catálogo de asignaturas.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: exportarAsignaturas()](../../../modelosUML/analisis/exportarAsignaturas/exportarAsignaturas-analisis.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### ExportarAsignaturasView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Presentar las opciones de exportación para las asignaturas.
- Informar sobre el estado de la generación del archivo.
- Facilitar la descarga del archivo al docente.

**Colaboraciones**:
- **Entrada**: Invocado por `exportarConfiguracionGlobal`.
- **Control**: Se comunica con `AsignaturaController`.

### clases de control

#### AsignaturaController
**Estereotipo**: Control  
**Responsabilidades**:
- Coordinar la obtención de las asignaturas del sistema.
- Solicitar la generación del archivo de exportación al componente exportador.

**Colaboraciones**:
- **Repositorio**: `AsignaturaRepository`.
- **Entidad**: `Exportador`.

### clases de entidad (entity)

#### AsignaturaRepository
**Estereotipo**: Entidad (Repositorio)  
**Responsabilidades**: Proveer acceso a los datos de las asignaturas almacenadas.

#### Exportador
**Estereotipo**: Entidad  
**Responsabilidades**: Aplicar el formato de transformación requerido a los datos de las entidades.

#### Asignatura
**Estereotipo**: Entidad  
**Responsabilidades**: Representar la entidad de negocio a exportar.
