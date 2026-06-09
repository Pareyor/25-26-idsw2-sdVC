# Jorgestor > crearAsignatura > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Análisis](/documents/analisis/crearAsignatura/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0
- **Fecha**: 2026-06-03
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica de la creación de asignaturas por parte del Docente. Se aplica el patrón "El Delgado" para una creación rápida y vinculación con un Grado existente.

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../images/diseño/crearAsignatura/secuencia.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/crearAsignatura/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente `AsignaturaCreate.tsx` que gestiona el formulario de alta y la selección del Grado.
- **AsignaturaController**: Endpoint `POST /api/asignaturas` protegido por `@PreAuthorize("hasRole('DOCENTE')")`.
- **AsignaturaService**: Lógica de negocio para verificar la unicidad del código de asignatura, validar la existencia del Grado a través de `GradoService` y persistir la entidad.
- **AsignaturaRepository**: Interface para la persistencia en base de datos de las asignaturas.
- **GradoService**: Servicio responsable de las operaciones sobre grados.
- **AsignaturaDTO**: Estructura de datos para la transferencia desde la vista.

## Decisiones de diseño

- **Validación de Unicidad**: El servicio verifica que el código de la asignatura no esté duplicado.
- **Vinculación con Grado**: La asignatura se asocia obligatoriamente a un Grado mediante su ID. El servicio valida la existencia del Grado a través de `GradoService`.
- **Seguridad**: Solo usuarios con el rol `ROLE_DOCENTE` pueden crear asignaturas.
- **Flujo de Usuario**: Tras la creación, el sistema redirige al listado general de asignaturas (`AsignaturaList`) con un mensaje de éxito.
- **Patrón de Creación**: Se utiliza el patrón "El Delgado", permitiendo la creación desde el listado y retornando a él tras completar la acción.
