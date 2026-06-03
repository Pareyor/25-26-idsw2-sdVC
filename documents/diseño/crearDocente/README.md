# Jorgestor > crearDocente > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoAdministradorInstitucional/diagramaContexto.svg)|[Análisis](/documents/analisis/crearDocente/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0
- **Fecha**: 2026-06-03
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica de la creación de docentes por parte del Administrador Institucional. Se aplica el patrón "El Delgado" para una creación rápida y redirección inmediata al listado.

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../modelosUML/diseño/crearDocente/secuencia.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/crearDocente/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente `DocenteCreate.tsx` que gestiona el formulario de alta.
- **DocenteController**: Endpoint `POST /api/docentes` protegido por `@PreAuthorize("hasRole('ADMIN')")`.
- **UsuarioService**: Lógica de negocio para verificar duplicidad de DNI y persistir el nuevo usuario con rol DOCENTE.
- **UsuarioRepository**: Interface para la persistencia en base de datos de usuarios.
- **DocenteDTO**: Estructura de datos para la transferencia desde la vista.

## Decisiones de diseño

- **Validación de Unicidad**: El servicio verifica que el DNI no esté registrado antes de proceder con la creación.
- **Seguridad**: Solo usuarios con `ROLE_ADMIN` pueden invocar este proceso.
- **Flujo de Usuario**: Tras la creación exitosa, el sistema redirige automáticamente al listado de docentes (`DocenteList`) mostrando un mensaje de confirmación.
- **Patrón de Creación**: Se utiliza el patrón "El Delgado", donde la vista de creación se invoca desde el listado y vuelve a él tras completar la acción.
