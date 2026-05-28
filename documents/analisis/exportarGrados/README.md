# Jorgestor > exportarGrados > Análisis

> |[🏠️](/README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#exportar-grados-docente)|**Análisis**|Diseño|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Fecha**: 2026-05-28
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso abstracto `exportarGrados()` mediante el patrón MVC. Este caso de uso es invocado por el proceso de exportación global para gestionar la salida de datos del catálogo de grados académicos.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: exportarGrados()](../../../modelosUML/analisis/exportarGrados/exportarGrados-analisis.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### ExportarGradosView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Presentar las opciones de exportación para los grados.
- Informar sobre el estado de la generación del archivo resultante.
- Facilitar la descarga del archivo al docente.

**Colaboraciones**:
- **Entrada**: Invocado por `exportarConfiguracionGlobal`.
- **Control**: Se comunica con `GradoController`.

### clases de control

#### GradoController
**Estereotipo**: Control  
**Responsabilidades**:
- Coordinar la recuperación de los grados del sistema.
- Solicitar la transformación de los datos al formato de exportación deseado.

**Colaboraciones**:
- **Repositorio**: `GradoRepository`.
- **Entidad**: `Exportador`.

### clases de entidad (entity)

#### GradoRepository
**Estereotipo**: Entidad (Repositorio)  
**Responsabilidades**: Proveer acceso a la persistencia de los datos de los grados.

#### Exportador
**Estereotipo**: Entidad  
**Responsabilidades**: Aplicar el formato de transformación (CSV, JSON, XML, etc.) a los datos de las entidades.

#### Grado
**Estereotipo**: Entidad  
**Responsabilidades**: Representar la entidad de negocio Grado.
