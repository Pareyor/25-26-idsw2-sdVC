# Jorgestor > crearGrado > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Análisis](/documents/analisis/crearGrado/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0
- **Fecha**: 2026-06-03
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica de la creación de grados académicos por parte del Docente. Se aplica el patrón "El Delgado" para una creación rápida y redirección inmediata al listado.

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../images/diseño/crearGrado/crearGrado.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/crearGrado/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente `GradoCreate.tsx` que gestiona el formulario de alta.
- **GradoController**: Endpoint `POST /api/grados` protegido por `@PreAuthorize("hasRole('DOCENTE')")`.
- **GradoService**: Lógica de negocio para verificar la unicidad del código de grado y persistir la entidad.
- **GradoRepository**: Interface para la persistencia en base de datos de los grados.
- **GradoDTO**: Estructura de datos para la transferencia desde la vista.

## Decisiones de diseño

- **Validación de Unicidad**: El servicio verifica que el código del grado no exista previamente en la base de datos.
- **Seguridad**: El acceso está restringido a usuarios con el rol `ROLE_DOCENTE`.
- **Flujo de Usuario**: Tras la creación exitosa, el sistema redirige automáticamente al listado de grados (`GradoList`) mostrando un mensaje de confirmación.
- **Patrón de Creación**: Se utiliza el patrón "El Delgado", facilitando la creación desde el listado y retornando a él tras completar la acción.
