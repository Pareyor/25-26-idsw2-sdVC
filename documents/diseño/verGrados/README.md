# Jorgestor > verGrados > Diseño

> |[🏠️](/documents/diseño/README.md)|[📊](../../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.svg)|[Análisis](/documents/analisis/verGrados/README.md)|**Diseño**|Desarrollo|Pruebas|
> |-|-|-|-|-|-|

## Información del artefacto

- **Proyecto**: Jorgestor - Sistema de Gestión de Exámenes
- **Fase RUP**: Elaboración
- **Disciplina**: Diseño
- **Versión**: 1.0
- **Fecha**: 2026-05-31
- **Autor**: Gemini CLI

## Propósito

Detallar la implementación técnica del listado de grados para el Docente. Este diseño asegura que los usuarios con rol `ROLE_DOCENTE` puedan visualizar y gestionar la oferta académica de grados en la que participan.

## Diagrama de secuencia de diseño

<div align=center>

|![Diagrama de Secuencia](../../../modelosUML/diseño/verGrados/secuencia.svg)|
|-|
|[Código PlantUML](../../../modelosUML/diseño/verGrados/secuencia.puml)|

</div>

## Participantes

- **Frontend (React)**: Componente `GradoList.tsx` que consume el endpoint `/api/grados`.
- **GradoController**: Endpoint `GET /api/grados` protegido por `@PreAuthorize("hasRole('DOCENTE')")`.
- **GradoService**: Lógica de negocio para la recuperación y filtrado de grados.
- **GradoRepository**: Interface JPA para la persistencia de la entidad `Grado`.
- **GradoDTO**: Objeto de transferencia para los datos del grado (`id`, `titulo`, `codigo`).

## Decisiones de diseño

- **Entidad Grado**: Se implementará la entidad `Grado` con los campos `titulo` y `codigo` según el modelo del dominio.
- **Seguridad**: Acceso permitido a docentes y administradores (aunque el caso de uso está priorizado para el docente).
- **Consistencia**: Se mantiene la estructura de tablas y buscadores similar a la de `verDocentes` para asegurar una experiencia de usuario coherente en todo el sistema.
