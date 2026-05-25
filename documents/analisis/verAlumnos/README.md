# Jorgestor > verAlumnos > Análisis

> |[🏠️](/README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#ver-alumnos-docente)|**Análisis**|Diseño|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Fecha**: 2026-05-25
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `verAlumnos()` mediante el patrón MVC, identificando las clases de análisis y sus responsabilidades para visualizar el listado de alumnos y permitir la navegación a acciones relacionadas.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: verAlumnos()](../../../modelosUML/analisis/verAlumnos/verAlumnos-analisis.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### VerAlumnosView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Presentar el listado de alumnos registrados.
- Proporcionar herramientas de búsqueda por NIU o nombre.
- Ofrecer accesos directos a la creación, edición y eliminación de alumnos.
- Permitir la importación masiva de alumnos.
- Facilitar la salida del módulo mediante la finalización de gestión.

**Colaboraciones**:
- **Entrada**: Recibe `verAlumnos()` desde `:MAIN_VIEW`.
- **Control**: Se comunica con `AlumnoController`.
- **Salida**: **<<include>>** `:Collaboration CrearAlumno`, `:Collaboration EditarAlumno`, `:Collaboration EliminarAlumno`, `:Collaboration ImportarAlumnos`, `:Collaboration CompletarGestion`.

### clases de control

#### AlumnoController
**Estereotipo**: Control  
**Responsabilidades**:
- Coordinar la recuperación de todos los alumnos.
- Gestionar los criterios de búsqueda aplicados por el usuario.
- Servir de puente entre la vista y el repositorio.

**Colaboraciones**:
- **Vista**: Responde a `VerAlumnosView`.
- **Repositorio**: Delega en `AlumnoRepository`.

### clases de entidad (entity)

#### AlumnoRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Proveer acceso a la persistencia de los alumnos.
- Recuperar la lista completa o filtrada de registros.

**Colaboraciones**:
- **Control**: Responde a `AlumnoController`.
- **Entidad**: Gestiona instancias de `Alumno`.

#### Alumno
**Estereotipo**: Entidad  
**Responsabilidades**:
- Almacenar los datos básicos de un alumno (NIU, nombre, apellidos, etc.).

## flujo de colaboración principal

1. **Inicio**: El Docente accede a la sección de alumnos desde la vista principal.
2. **Consulta**: `VerAlumnosView` solicita el listado al `AlumnoController`.
3. **Recuperación**: `AlumnoController` solicita los datos al `AlumnoRepository`.
4. **Respuesta**: Los datos fluyen de vuelta hasta la vista.
5. **Visualización**: La vista renderiza la tabla con buscador y botones de acción.
6. **Navegación**: El Docente selecciona una acción (Crear, Editar, Eliminar, Importar o Finalizar).
