# Jorgestor > iniciarSesion > Análisis

> |[🏠️](/README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#iniciar-sesión-docente-y-administrador-institucional)|**Análisis**|Diseño|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.1
- **Fecha**: 2026-05-26
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `iniciarSesion()` mediante el patrón MVC, identificando las clases de análisis y la lógica de autenticación necesaria. Se incluye la creación de la sesión de usuario y la transición al estado disponible del sistema.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: iniciarSesion()](../../../images/analisis/iniciarSesion/iniciarSesion-analisis.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

### diagrama de secuencia
<div align=center>

|![Secuencia: iniciarSesion()](../../../images/analisis/iniciarSesion/secuencia.svg)|
|-|
|Código fuente: [secuencia.puml](secuencia.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### IniciarSesionView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Proporcionar la interfaz de entrada de credenciales (Usuario y Contraseña).
- Notificar errores de autenticación al usuario.
- Gestionar la transición al estado principal del sistema tras un éxito mediante la colaboración `SistemaDisponible`.

**Colaboraciones**:
- **Entrada**: Solicitud desde el estado inicial `:SESION_CERRADA`.
- **Control**: Se comunica con `SesionController`.
- **Salida**: **<<include>>** `:Collaboration SistemaDisponible`.

### clases de control

#### SesionController
**Estereotipo**: Control  
**Responsabilidades**:
- Procesar la solicitud de autenticación.
- Validar las credenciales proporcionadas contra el repositorio.
- **Crear y gestionar la instancia de `Sesion` tras una autenticación exitosa.**

**Colaboraciones**:
- **Vista**: Responde a `IniciarSesionView`.
- **Repositorio**: Delega en `UsuarioRepository`.
- **Entidad**: Crea instancias de `Sesion`.

### clases de entidad (entity)

#### UsuarioRepository
**Estereotipo**: Entidad  
**Responsabilidades**:
- Consultar la persistencia para verificar la existencia y validez de un usuario por sus credenciales.

**Colaboraciones**:
- **Control**: Responde a `SesionController`.

#### Usuario
**Estereotipo**: Entidad  
**Responsabilidades**:
- Representar al usuario autenticado (Docente o Administrador) y sus permisos asociados.

#### Sesion
**Estereotipo**: Entidad  
**Responsabilidades**:
- Representar la sesión activa de un usuario en el sistema.
- Mantener la referencia al `Usuario` autenticado durante el tiempo de vida de la sesión.

## flujo de colaboración principal

### secuencia: iniciar sesión

1. **Inicio**: El sistema se encuentra en el estado `:SESION_CERRADA` y presenta la `IniciarSesionView`.
2. **Entrada**: El usuario introduce sus datos y pulsa "Entrar".
3. **Validación**: `SesionController` solicita la búsqueda del usuario al `UsuarioRepository`.
4. **Creación de Sesión**: Tras validar al usuario, el controlador instancia un objeto `Sesion`.
5. **Decisión**:
    - **Si es correcto**: Se autoriza el acceso y se ejecuta `sistemaDisponible(usuario)`.
    - **Si es incorrecto**: Se informa del error y se permanece en la vista de login.

## control de acceso y gestión de sesión

Este caso de uso asegura que el acceso al sistema esté respaldado por un objeto `Sesion` que vincula al usuario con sus acciones posteriores en el sistema disponible.
