# Jorgestor > verDocentes > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoAdministradorInstitucional/diagramaContexto.svg)|[Análisis](/documents/analisis/verDocentes/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0
- **Fecha**: 2026-05-31
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica del listado de docentes para el Administrador Institucional. Este diseño asegura que solo los usuarios con rol `ROLE_ADMIN` puedan acceder al listado de usuarios con rol `ROLE_DOCENTE`.

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../images/diseño/verDocentes/secuencia.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/verDocentes/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente `DocenteList.tsx` que consume el endpoint `/api/docentes`.
- **DocenteController**: Endpoint `GET /api/docentes` protegido por `@PreAuthorize("hasRole('ADMIN')")`.
- **UsuarioService**: Lógica para filtrar usuarios por el rol `ROLE_DOCENTE`.
- **UsuarioRepository**: Interface para acceder a la base de datos de usuarios.

## Decisiones de diseño

- **Seguridad**: El acceso al listado está restringido estrictamente a administradores.
- **DTO**: Se utilizará `DocenteDTO` para encapsular solo la información relevante (DNI, Nombre, Apellidos, Email, Departamento) y no exponer datos sensibles de la entidad `Usuario`.
- **Buscador (Frontend)**: Se implementará un filtrado reactivo en el frontend sobre la lista cargada inicialmente para mejorar la experiencia de usuario.
