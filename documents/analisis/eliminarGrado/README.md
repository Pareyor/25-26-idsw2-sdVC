# Jorgestor > eliminarGrado > Análisis

> |[🏠️](/README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#eliminar-grado-docente)|**Análisis**|Diseño|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Fecha**: 2026-05-26
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `eliminarGrado()` mediante el patrón MVC, identificando las clases de análisis, sus responsabilidades y colaboraciones necesarias para gestionar la eliminación de grados académicos y sus impactos asociados.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: eliminarGrado()](../../../images/analisis/eliminarGrado/eliminarGrado-analisis.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### EliminarGradoView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Presentar la información del grado a eliminar (Nombre, Código).
- Informar sobre el número de alumnos enlistados afectados.
- Solicitar confirmación definitiva al docente ante una acción irreversible.

**Colaboraciones**:
- **Entrada**: Recibe `eliminarGrado(id)` desde `:GRADOS_ABIERTO` o `:GRADO_ABIERTO`.
- **Control**: Se comunica con `GradoController`.
- **Salida**: **<<include>>** `:Collaboration VerGrados`.

### clases de control

#### GradoController
**Estereotipo**: Control  
**Responsabilidades**:
- Orquestar la lógica de negocio para la eliminación de un grado.
- Validar si existen restricciones de integridad referencial.
- Comunicar el resultado de la operación a la interfaz de usuario.

**Colaboraciones**:
- **Vista**: Responde a `EliminarGradoView`.
- **Repositorio**: Delega en `GradoRepository`.

### clases de entidad (entity)

#### GradoRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Abstraer la persistencia de los datos de los grados.
- Ejecutar la sentencia de eliminación del registro correspondiente.

**Colaboraciones**:
- **Control**: Responde a `GradoController`.
- **Entidad**: Maneja instancias de `Grado`.

#### Grado
**Estereotipo**: Entidad  
**Responsabilidades**:
- Almacenar los datos del grado: nombre, código y relaciones.

## flujo de colaboración principal

### secuencia: eliminar grado

1. **Inicio**: Solicitud de eliminación desde la lista de grados o desde la edición de un grado específico.
2. **Presentación**: `EliminarGradoView` presenta el resumen del grado y la advertencia de seguridad.
3. **Confirmación**: El docente confirma la eliminación tras revisar los datos.
4. **Borrado**: `GradoController` solicita la eliminación al `GradoRepository`.
5. **Finalización**: Redirección automática a la vista de gestión de grados.

## consideraciones de integridad

La eliminación de un grado implica considerar la situación de los alumnos enlistados, siguiendo la advertencia presentada en el prototipo sobre la irreversibilidad de la acción.
