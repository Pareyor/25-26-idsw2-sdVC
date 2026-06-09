# Jorgestor > eliminarAsignatura > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Análisis](/documents/analisis/eliminarAsignatura/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0
- **Fecha**: 2026-06-03
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica de la eliminación de una asignatura por parte del Docente. Este proceso incluye una fase de confirmación previa en la interfaz de usuario para evitar borrados accidentales.

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../images/diseño/eliminarAsignatura/secuencia.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/eliminarAsignatura/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente `AsignaturaList.tsx` que gestiona la interacción y la petición de borrado.
- **AsignaturaController**: Endpoint `DELETE /api/asignaturas/{id}` protegido por `@PreAuthorize("hasRole('DOCENTE')")`.
- **AsignaturaService**: Lógica para verificar la existencia de la asignatura y ejecutar la eliminación.
- **AsignaturaRepository**: Interface para interactuar con la persistencia y eliminar el registro.

## Decisiones de diseño

- **Confirmación en UI**: Antes de realizar la petición al servidor, el frontend mostrará un cuadro de diálogo de confirmación.
- **Seguridad**: Solo usuarios con el rol `ROLE_DOCENTE` tienen permiso para eliminar asignaturas.
- **Respuesta HTTP**: Se utilizará el código de estado `204 No Content` tras una eliminación exitosa.
- **Integridad Referencial**: El servicio debe asegurar que no existan dependencias críticas (como preguntas vinculadas) antes de borrar, o manejar la lógica de negocio correspondiente (borrado en cascada de preguntas o restricción).
- **Refresco Visual**: Tras la eliminación, el listado de asignaturas se actualizará automáticamente eliminando la fila correspondiente.
