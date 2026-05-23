# importarConfiguracionGlobal() (Análisis)

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `importarConfiguracionGlobal()` mediante el patrón MVC, identificando las clases de análisis para la importación masiva de datos del sistema.

## diagrama de colaboración

<div align=center>

|![Análisis: importarConfiguracionGlobal()](../../../modelosUML/analisis/importarConfiguracionGlobal/colaboracion.svg)|
|-|
|Código fuente: [colaboracion.puml](../../../modelosUML/analisis/importarConfiguracionGlobal/colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### ImportarConfiguracionView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Permitir al docente seleccionar archivos de configuración.
- Mostrar opciones de importación (alumnos, grados, preguntas, etc.).
- Informar sobre el progreso y éxito/error de la importación.

**Colaboraciones**:
- **Control**: `ConfiguracionController`.

### clases de control

#### ConfiguracionController
**Estereotipo**: Control  
**Responsabilidades**:
- Coordinar el flujo de importación global.
- Delegar el parseo de archivos al `Importador`.
- Orquestar la actualización de los diferentes repositorios.

### clases de entidad (entity)

#### Importador
**Estereotipo**: Entidad  
**Responsabilidades**:
- Procesar archivos externos (JSON, CSV, etc.).
- Validar el formato de los datos importados.

#### ConfiguracionRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Actualizar el estado global del sistema con los nuevos datos.
- Asegurar la integridad referencial entre entidades importadas.
