# Jorgestor > eliminarDocente > Análisis

> |[🏠️](/README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoAdministradorInstitucional/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#eliminar-docente-administrador-institucional)|**Análisis**|Diseño|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Fecha**: 2026-05-26
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `eliminarDocente()` mediante el patrón MVC, identificando las clases de análisis, sus responsabilidades y colaboraciones necesarias para que el Administrador Institucional gestione la eliminación de perfiles docentes.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: eliminarDocente()](../../../images/analisis/eliminarDocente/eliminarDocente.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### EliminarDocenteView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Presentar los datos del docente a eliminar (Nombre, Apellidos, DNI, Usuario, Email).
- Mostrar advertencia de seguridad sobre la irreversibilidad de la acción.
- Recoger la decisión final del Administrador Institucional.

**Colaboraciones**:
- **Entrada**: Recibe `eliminarDocente(id)` desde `:DOCENTES_ABIERTO` o `:DOCENTE_ABIERTO`.
- **Control**: Se comunica con `DocenteController`.
- **Salida**: **<<include>>** `:Collaboration VerDocentes`.

### clases de control

#### DocenteController
**Estereotipo**: Control  
**Responsabilidades**:
- Gestionar la lógica de baja de usuarios docentes en el sistema.
- Validar permisos de administrador antes de ejecutar la acción.
- Coordinar la actualización de la vista tras la operación.

**Colaboraciones**:
- **Vista**: Responde a `EliminarDocenteView`.
- **Repositorio**: Delega en `DocenteRepository`.

### clases de entidad (entity)

#### DocenteRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Proveer acceso persistente a los datos de los docentes.
- Ejecutar la eliminación física o lógica del registro del docente.

**Colaboraciones**:
- **Control**: Responde a `DocenteController`.
- **Entidad**: Maneja instancias de `Docente`.

#### Docente
**Estereotipo**: Entidad  
**Responsabilidades**:
- Almacenar los atributos del docente: nombre, apellidos, DNI, usuario, email, password.

## flujo de colaboración principal

### secuencia: eliminar docente

1. **Inicio**: El Administrador Institucional solicita eliminar a un docente desde la lista general o desde el detalle del docente.
2. **Presentación**: `EliminarDocenteView` muestra la información completa del perfil y el aviso de irreversibilidad.
3. **Confirmación**: El Administrador confirma la eliminación.
4. **Borrado**: `DocenteController` solicita al `DocenteRepository` la eliminación por ID.
5. **Finalización**: El sistema redirige automáticamente a la vista de gestión de docentes (`VerDocentes`).

## seguridad e integridad

Este caso de uso es crítico por afectar al acceso de los usuarios docentes. Se asegura que la acción solo pueda ser ejecutada por el Administrador Institucional mediante una confirmación explícita.
