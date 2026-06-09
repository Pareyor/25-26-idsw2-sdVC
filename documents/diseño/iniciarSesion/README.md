# Jorgestor > iniciarSesion > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](https://github.com/martinlopez7/25-26-IdSw1-SdR/blob/main/documents/casos-de-uso/detalladoCasosDeUso/iniciarSesion/iniciarSesion.svg)|[Análisis](/documents/analisis/iniciarSesion/README.md)|**Diseño**|
> |-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0 (Spring Boot + React + JWT)
- **Fecha**: 2026-05-30
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica de la autenticación de usuarios mediante el flujo de intercambio de credenciales por un token JWT. Este diseño asegura que el sistema sea stateless y seguro, siguiendo las directrices del stack tecnológico.

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../images/diseño/iniciarSesion/secuencia.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/iniciarSesion/secuencia.puml)|

</div>

## Participantes

- **Frontend (React + TypeScript)**: Componente `LoginComponent` que gestiona el formulario, la llamada al endpoint de login y el almacenamiento del JWT.
- **AuthController**: Endpoint REST `POST /api/auth/login` que recibe las credenciales.
- **AuthService**: Orquestador de la lógica de autenticación. Utiliza `AuthenticationManager` de Spring Security para validar al usuario.
- **JwtProvider**: Componente encargado de la generación, firma y validación de los tokens JWT.
- **UsuarioRepository**: Interface JPA para la consulta de usuarios y sus roles en PostgreSQL.
- **Base de Datos (PostgreSQL)**: Almacén persistente de usuarios con contraseñas cifradas (BCrypt).

## Decisiones de diseño

- **Seguridad Stateless**: Uso de JWT para evitar el mantenimiento de sesiones en el servidor.
- **Cifrado**: Las contraseñas se validan mediante `BCryptPasswordEncoder`.
- **Almacenamiento en Cliente**: El token se almacena en `localStorage` (o alternativamente en una cookie HttpOnly según requerimientos de seguridad adicionales) para ser incluido en la cabecera `Authorization: Bearer <token>` de futuras peticiones.
- **DTOs**: Uso de `LoginRequest` para la entrada y `AuthResponse` para la salida, ocultando detalles internos de la entidad `Usuario`.
- **Manejo de Errores**: Retorno de `401 Unauthorized` en caso de fallo, proporcionando una respuesta genérica por seguridad.
