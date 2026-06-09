# Jorgestor > cerrarSesion > Análisis

> |[🏠️](/README.md)|[ 📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Detalle](../../../archivosEsenciales/casos-de-uso/detalladoCasosDeUso/README.md#cerrar-sesión-docente-y-administrador-institucional)|**Análisis**|Diseño|Desarrollo|Pruebas|
> |-|-|-|-|-|-|-|

## información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Análisis y Diseño
- **Versión**: 1.1
- **Fecha**: 2026-05-27
- **Autor**: Gemini CLI

## propósito

Análisis de colaboración del caso de uso `cerrarSesion()` mediante el patrón MVC, asegurando la finalización segura de la sesión activa para el usuario actual y el retorno al estado inicial del sistema, o el mantenimiento del estado actual en caso de cancelación.

## diagramas de análisis

### diagrama de colaboración
<div align=center>

|![Análisis: cerrarSesion()](../../../images/analisis/cerrarSesion/cerrarSesion.svg)|
|-|
|Código fuente: [colaboracion.puml](colaboracion.puml)|

</div>

### diagrama de secuencia
<div align=center>

|![Secuencia: cerrarSesion()](../../../images/analisis/cerrarSesion/cerrarSesion.svg)|
|-|
|Código fuente: [secuencia.puml](secuencia.puml)|

</div>

## clases de análisis identificadas

### clases de vista (boundary)

#### CerrarSesionView
**Estereotipo**: Vista (Boundary)  
**Responsabilidades**:
- Presentar el diálogo de confirmación de cierre de sesión.
- Mostrar el identificador del usuario actual.
- Capturar la confirmación o la cancelación de la acción.
- Redirigir a `:SESION_CERRADA` tras confirmar.
- Retornar a `:SISTEMA_DISPONIBLE` si se cancela.

**Colaboraciones**:
- **Entrada**: Recibe `cerrarSesion()` desde `:SISTEMA_DISPONIBLE`.
- **Control**: Se comunica con `CerrarSesionController`.
- **Salida**: Redirige a `:SESION_CERRADA` o retorna a `:SISTEMA_DISPONIBLE`.

### clases de control

#### CerrarSesionController
**Estereotipo**: Control  
**Responsabilidades**:
- Coordinar la invalidación de la sesión del usuario específico.
- Notificar el éxito de la destrucción de la sesión a la vista.

**Colaboraciones**:
- **Vista**: Responde a `CerrarSesionView`.
- **Entidad**: Gestiona la instancia de `Sesion`.

### clases de entidad (entity)

#### Sesion
**Estereotipo**: Entidad  
**Responsabilidades**:
- Representar la sesión activa del usuario.
- Exponer el método `eliminarSesion(usuario)` para finalizar la instancia.

## flujo de colaboración principal

### secuencia: cerrar sesión

1. **Inicio**: El usuario solicita cerrar sesión desde el menú principal.
2. **Confirmación**: `CerrarSesionView` solicita confirmación.
3. **Cierre**: Si se confirma, `CerrarSesionController.cerrarSesion(usuario)` es invocado.
4. **Destrucción**: Se ejecuta `eliminarSesion(usuario)` sobre la entidad `Sesion`.
5. **Cancelación**: Si se cancela, la vista simplemente redirige de vuelta a `:SISTEMA_DISPONIBLE`.
6. **Finalización**: Tras el éxito, el sistema transita al estado `SESION_CERRADA`.
