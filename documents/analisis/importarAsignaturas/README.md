# Jorgestor > importarAsignaturas > Análisis

> |[🏠️](/README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#importar-asignaturas-docente)|**Análisis**|Diseño|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.1
- **Fecha**: 2026-05-28
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `importarAsignaturas()` mediante el patrón MVC. Este caso de uso permite la creación masiva de asignaturas a partir de un archivo externo, facilitando la configuración inicial del sistema y la reutilización de datos existentes.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: importarAsignaturas()](../../../modelosUML/analisis/importarAsignaturas/importarAsignaturas-analisis.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### ImportarAsignaturasView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Permitir la selección del archivo de origen para la importación.
- Mostrar el estado del proceso de carga y validación.
- Capturar la confirmación final del docente para persistir los datos.

**Colaboraciones**:
- **Entrada**: `importarAsignaturas()` desde `:ASIGNATURAS_ABIERTO`.
- **Control**: `AsignaturaController`.
- **Salida**: Redirige a `VerAsignaturas`.

### clases de control

#### AsignaturaController
**Estereotipo**: Control  
**Responsabilidades**:
- Coordinar la lectura y el parseo del archivo de asignaturas.
- Validar la integridad y el formato de los datos importados.
- Invocar la creación masiva en el repositorio.

**Colaboraciones**:
- **Repositorio**: `AsignaturaRepository`.

### clases de entidad (entity)

#### AsignaturaRepository
**Estereotipo**: Entidad (Repositorio)  
**Responsabilidades**: Proveer métodos para la inserción masiva de entidades `Asignatura`.

#### Asignatura
**Estereotipo**: Entidad  
**Responsabilidades**: Representar cada una de las asignaturas a importar.

## flujo de colaboración principal

### secuencia: importar asignaturas

1. **Carga**: El docente selecciona un archivo y solicita su carga.
2. **Validación**: El controlador parsea el archivo y valida que los campos obligatorios estén presentes y correctos.
3. **Previsualización**: El sistema muestra un resumen de las asignaturas detectadas.
4. **Persistencia**: Al confirmar, el controlador recorre la lista y solicita al repositorio el guardado de cada entidad.
5. **Finalización**: Se informa del resultado (éxito/error) y se vuelve al listado de asignaturas.
