# Jorgestor > verAsignaturas > Análisis

> |[🏠️](/README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#ver-asignaturas-docente)|**Análisis**|Diseño|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Fecha**: 2026-05-25
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `verAsignaturas()` mediante el patrón MVC, identificando las clases de análisis y sus responsabilidades para visualizar el listado de asignaturas y permitir la navegación a acciones relacionadas.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: verAsignaturas()](../../../images/analisis/verAsignaturas/verAsignaturas-analisis.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### VerAsignaturasView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Presentar el listado de asignaturas registradas.
- Proporcionar herramientas de búsqueda y filtrado.
- Ofrecer accesos directos a la creación, edición y eliminación.
- Permitir la importación masiva de asignaturas.
- Facilitar la salida del módulo mediante la finalización de gestión.

**Colaboraciones**:
- **Entrada**: Recibe `verAsignaturas()` desde `:MAIN_VIEW`.
- **Control**: Se comunica con `AsignaturaController`.
- **Salida**: **<<include>>** `:Collaboration CrearAsignatura`, `:Collaboration EditarAsignatura`, `:Collaboration EliminarAsignatura`, `:Collaboration ImportarAsignatura`, `:Collaboration CompletarGestion`.

### clases de control

#### AsignaturaController
**Estereotipo**: Control  
**Responsabilidades**:
- Coordinar la recuperación de todas las asignaturas.
- Gestionar los criterios de búsqueda aplicados por el usuario.
- Servir de puente entre la vista y el repositorio.

**Colaboraciones**:
- **Vista**: Responde a `VerAsignaturasView`.
- **Repositorio**: Delega en `AsignaturaRepository`.

### clases de entidad (entity)

#### AsignaturaRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Proveer acceso a la persistencia de las asignaturas.
- Recuperar la lista completa o filtrada de registros.

**Colaboraciones**:
- **Control**: Responde a `AsignaturaController`.
- **Entidad**: Gestiona instancias de `Asignatura`.

#### Asignatura
**Estereotipo**: Entidad  
**Responsabilidades**:
- Almacenar los datos básicos de una asignatura (ID, código, título, etc.).

## flujo de colaboración principal

1. **Inicio**: El Docente accede a la sección de asignaturas desde la vista principal.
2. **Consulta**: `VerAsignaturasView` solicita el listado al `AsignaturaController`.
3. **Recuperación**: `AsignaturaController` solicita los datos al `AsignaturaRepository`.
4. **Respuesta**: Los datos fluyen de vuelta hasta la vista.
5. **Visualización**: La vista renderiza la tabla con buscador y botones de acción.
6. **Navegación**: El Docente selecciona una acción (Crear, Editar, Eliminar, Importar o Finalizar).
