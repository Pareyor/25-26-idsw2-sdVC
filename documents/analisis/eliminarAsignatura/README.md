# Jorgestor > eliminarAsignatura > Análisis

> |[🏠️](/README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#eliminar-asignatura-docente)|**Análisis**|Diseño|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Fecha**: 2026-05-26
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `eliminarAsignatura()` mediante el patrón MVC, identificando las clases de análisis, sus responsabilidades y colaboraciones necesarias para asegurar una eliminación segura y confirmada de las asignaturas del sistema.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: eliminarAsignatura()](../../../modelosUML/analisis/eliminarAsignatura/eliminarAsignatura-analisis.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### EliminarAsignaturaView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Presentar los datos clave de la asignatura a eliminar (Código, Nombre, Curso).
- Mostrar advertencia de irreversibilidad al docente.
- Solicitar confirmación final de eliminación o permitir la cancelación.

**Colaboraciones**:
- **Entrada**: Recibe `eliminarAsignatura(id)` desde `:ASIGNATURAS_ABIERTO` o `:ASIGNATURA_ABIERTO`.
- **Control**: Se comunica con `AsignaturaController` para confirmar la acción.
- **Salida**: **<<include>>** `:Collaboration VerAsignaturas`.

### clases de control

#### AsignaturaController
**Estereotipo**: Control  
**Responsabilidades**:
- Coordinar la lógica de eliminación definitiva de la asignatura.
- Validar las condiciones de borrado si fuera necesario.
- Notificar el éxito o fracaso de la operación a la vista.

**Colaboraciones**:
- **Vista**: Responde a `EliminarAsignaturaView`.
- **Repositorio**: Delega en `AsignaturaRepository`.

### clases de entidad (entity)

#### AsignaturaRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Gestionar la persistencia y eliminación física del registro de asignatura.
- Asegurar la limpieza de relaciones vinculadas si procede.

**Colaboraciones**:
- **Control**: Responde a `AsignaturaController`.
- **Entidad**: Referencia a la clase `Asignatura`.

#### Asignatura
**Estereotipo**: Entidad  
**Responsabilidades**:
- Representar los datos de la asignatura durante el proceso de confirmación.

## flujo de colaboración principal

### secuencia: eliminar asignatura

1. **Inicio**: El docente solicita eliminar una asignatura desde la lista o el detalle.
2. **Presentación**: `EliminarAsignaturaView` muestra los datos y la advertencia de seguridad.
3. **Confirmación**: El docente pulsa "Confirmar eliminación".
4. **Ejecución**: `AsignaturaController` solicita `confirmarEliminacion(id)` al repositorio.
5. **Persistencia**: `AsignaturaRepository` elimina físicamente el registro.
6. **Finalización**: El sistema redirige automáticamente a la vista de lista de asignaturas.

## política de eliminación segura

Este caso de uso implementa una política de eliminación destructiva con confirmación explícita para evitar pérdidas accidentales de información académica crítica.
