# Jorgestor > cerrarSesion > Análisis

> |[🏠️](/README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#cerrar-sesión-docente-y-administrador-institucional)|**Análisis**|Diseño|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.0
- **Fecha**: 2026-05-27
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `cerrarSesion()` mediante el patrón MVC, asegurando la finalización segura de la sesión activa y el retorno al estado inicial del sistema.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: cerrarSesion()](../../../modelosUML/analisis/cerrarSesion/cerrarSesion-analisis.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

### diagrama de secuencia
<div align=center>

|![Secuencia: cerrarSesion()](../../../modelosUML/analisis/cerrarSesion/secuencia.svg)|
|-|
|Código fuente: [secuencia.puml](secuencia.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### CerrarSesionView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Presentar el diálogo de confirmación de cierre de sesión.
- Mostrar información del usuario actual.
- Capturar la intención de confirmar o cancelar el cierre.
- Redirigir al estado `SESION_CERRADA` tras el éxito.

**Colaboraciones**:
- **Entrada**: Recibe `cerrarSesion()` desde `:SISTEMA_DISPONIBLE`.
- **Control**: Se comunica con `SesionController`.
- **Salida**: Redirige a `:SESION_CERRADA`.

### clases de control

#### SesionController
**Estereotipo**: Control  
**Responsabilidades**:
- Coordinar la destrucción de la sesión activa.
- Invalidar tokens o credenciales en memoria si aplica.
- Notificar el éxito de la operación a la vista.

**Colaboraciones**:
- **Vista**: Responde a `CerrarSesionView`.
- **Entidad**: Gestiona la instancia de `Sesion`.

### clases de entidad (entity)

#### Sesion
**Estereotipo**: Entidad  
**Responsabilidades**:
- Representar la sesión activa del usuario.
- Mantener referencia al Usuario autenticado.
- Ser destruida tras el cierre de sesión.

## flujo de colaboración principal

### secuencia: cerrar sesión

1. **Inicio**: El Docente o Administrador solicita cerrar sesión desde el menú principal.
2. **Confirmación**: `CerrarSesionView` solicita confirmación al usuario.
3. **Cierre**: `SesionController.cerrarSesion()` es invocado.
4. **Destrucción**: El controlador elimina la instancia de `Sesion`.
5. **Finalización**: La vista redirige al sistema al estado `SESION_CERRADA`.
