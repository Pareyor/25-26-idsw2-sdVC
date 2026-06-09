# Jorgestor > editarGrado > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Análisis](/documents/analisis/editarGrado/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0
- **Fecha**: 2026-06-03
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica de la edición de datos de un grado académico existente por parte del Docente. Se aplica el patrón "El Gordo" para permitir la edición integral de los campos (Título, Código).

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../images/diseño/editarGrado/secuencia.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/editarGrado/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente `GradoEdit.tsx` que gestiona la carga de datos inicial y el formulario de modificación.
- **GradoController**: Endpoints `GET /api/grados/{id}` y `PUT /api/grados/{id}` protegidos por `@PreAuthorize("hasRole('DOCENTE')")`.
- **GradoService**: Lógica para recuperar la entidad, validar cambios y persistir la actualización.
- **GradoRepository**: Interface para interactuar con la persistencia de los grados.
- **GradoDTO**: Clase para transferir los datos del grado entre capas.

## Decisiones de diseño

- **Carga Previa**: Se realiza una petición GET inicial para asegurar que el usuario edita la versión más reciente del grado.
- **Integridad**: El servicio verifica la existencia del grado antes de actualizar (`404 Not Found` si no existe).
- **Seguridad**: Solo usuarios con el rol `ROLE_DOCENTE` pueden realizar estas operaciones.
- **Flujo de Usuario**: Tras guardar los cambios, el sistema redirige al listado general para confirmar visualmente la actualización.
- **Patrón de Edición**: Se utiliza el patrón "El Gordo", permitiendo la edición de todos los campos visibles en una única operación.
