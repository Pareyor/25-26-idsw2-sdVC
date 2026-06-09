# Jorgestor > editarDocente > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoAdministradorInstitucional/diagramaContexto.svg)|[Análisis](/documents/analisis/editarDocente/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0
- **Fecha**: 2026-06-03
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica de la edición de datos de un docente existente por parte del Administrador Institucional. Se aplica el patrón "El Gordo" para permitir la edición integral de los campos del usuario.

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../images/diseño/editarDocente/secuencia.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/editarDocente/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente `DocenteEdit.tsx` que gestiona la carga de datos y el formulario de modificación.
- **DocenteController**: Endpoints `GET /api/docentes/{id}` y `PUT /api/docentes/{id}` protegidos por `@PreAuthorize("hasRole('ADMIN')")`.
- **UsuarioService**: Lógica para recuperar el usuario y aplicar los cambios antes de persistir.
- **UsuarioRepository**: Interface para acceder y actualizar la base de datos de usuarios.
- **DocenteDTO**: Clase para transferir los datos del docente entre las capas.

## Decisiones de diseño

- **Carga Previa**: La vista de edición realiza una petición GET inicial para asegurar que el formulario muestra los datos más recientes del servidor.
- **Integridad**: El servicio verifica la existencia del usuario antes de proceder con la actualización (`404 Not Found` si no existe).
- **Seguridad**: Solo usuarios con `ROLE_ADMIN` pueden modificar datos de otros usuarios.
- **Flujo de Usuario**: Tras la actualización, el sistema redirige al listado general para confirmar visualmente los cambios.
- **Patrón de Edición**: Se utiliza el patrón "El Gordo", permitiendo modificar todos los campos editables (Nombre, Apellidos, Email, Departamento) en una sola operación.
