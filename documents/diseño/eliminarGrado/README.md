# Jorgestor > eliminarGrado > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Análisis](/documents/analisis/eliminarGrado/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0
- **Fecha**: 2026-06-03
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica de la eliminación de un grado académico por parte del Docente. Este proceso incluye una fase de confirmación previa en la interfaz de usuario para evitar borrados accidentales.

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../modelosUML/diseño/eliminarGrado/secuencia.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/eliminarGrado/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente `GradoList.tsx` que gestiona la interacción y la petición de borrado.
- **GradoController**: Endpoint `DELETE /api/grados/{id}` protegido por `@PreAuthorize("hasRole('DOCENTE')")`.
- **GradoService**: Lógica para verificar la existencia del grado y ejecutar la eliminación.
- **GradoRepository**: Interface para interactuar con la persistencia y eliminar el registro.

## Decisiones de diseño

- **Confirmación en UI**: Antes de realizar la petición al servidor, el frontend mostrará un cuadro de diálogo de confirmación.
- **Seguridad**: Solo usuarios con el rol `ROLE_DOCENTE` tienen permiso para eliminar grados.
- **Respuesta HTTP**: Se utilizará el código de estado `204 No Content` tras una eliminación exitosa.
- **Integridad Referencial**: El servicio debe asegurar que no existan dependencias críticas (como asignaturas vinculadas) antes de borrar, o manejar la lógica de negocio correspondiente.
- **Refresco Visual**: Tras la eliminación, el listado de grados se actualizará automáticamente eliminando la fila correspondiente.
