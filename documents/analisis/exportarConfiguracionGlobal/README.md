# exportarConfiguracionGlobal() (Análisis)

## información del artefacto

- **Proyecto**: IdSw1-SdR / VC
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración para la exportación del estado completo del sistema.

## diagrama de colaboración

<div align=center>

|![Análisis: exportarConfiguracionGlobal()](../../../modelosUML/analisis/exportarConfiguracionGlobal/colaboracion.svg)|
|-|
|Código fuente: [colaboracion.puml](../../../modelosUML/analisis/exportarConfiguracionGlobal/colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### ExportarConfiguracionView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Permitir la selección de datos a exportar.
- Gestionar la descarga del archivo resultante.

### clases de control

#### ConfiguracionController
**Estereotipo**: Control  
**Responsabilidades**:
- Coordinar la extracción de datos de todos los repositorios.
- Delegar la generación del archivo al `Exportador`.

### clases de entidad (entity)

#### Exportador
**Estereotipo**: Entidad  
**Responsabilidades**:
- Transformar los datos del sistema a un formato intercambiable.

#### ConfiguracionRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Proporcionar una vista consolidada de los datos del sistema.
