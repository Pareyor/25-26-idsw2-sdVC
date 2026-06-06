# Registro de Conversación - Jorgestor (IDSW2)

## Sesión 1: [20 de mayo de 2026][17:38]

### Resumen de actividad:
- **Arranque del proyecto**: Análisis de requerimientos legados de Jorgestor (Ingeniería de Software 1).
- **Definición de contexto**: Se establece el alcance: CRUDs, gestión de preguntas, exámenes, sesiones y asignación.
- **Acuerdos**: Se utilizará Spring Boot (Java), React (TypeScript) con Tailwind CSS, y PostgreSQL.
- **Estructura**: Se organizará el proyecto para separar claramente Frontend y Backend.
- **Decisiones**: Se decide usar `JWT` para la autenticación y `Docker` para gestionar la base de datos de desarrollo.

**Decisión:** Se acuerda la estructura de directorios y se establece el flujo de trabajo: Diseño -> Implementación -> Validación.

## Sesión 2: [20 de mayo de 2026][18:00] Configuración Inicial y Estructura

**Prompt:** Configurar la estructura base del proyecto, Docker, y la entidad básica para Usuarios (Docente/Administrador).

**Resultado:**
- **Entorno**: Docker configurado con PostgreSQL.
- **Backend**: Proyecto Spring Boot iniciado con dependencias base (Web, Data JPA, Security, JWT).
- **Entidades**: Definición de la entidad `Usuario` con roles `ADMIN` y `DOCENTE`.

**Decisión:** Se ha configurado la infraestructura básica. Se utilizará Spring Security con JWT para proteger los endpoints.

## Sesión 19: [04/06/2026][16:30] Diseño y Auditoría de Módulos CRUD

**Prompt:** Diseño y revisión del CRUD de preguntas y respuestas.

**Resultado:**
- Inicialización de contexto mediante el protocolo "Inicio".
- Generación de documentación de diseño (`README.md`) y diagramas de secuencia (`.puml`) para `crearPregunta`, `editarPregunta` y `eliminarPregunta`.
- Auditoría arquitectónica: Se revisaron todos los diseños previos para asegurar que las dependencias entre módulos utilicen siempre la capa de Servicio (`Service`) y nunca accedan directamente al Repositorio de otro módulo (`Repository`).
- Corrección de `crearAsignatura`, `editarAsignatura`, `crearAlumno` y `editarAlumno` para cumplir con el estándar Service-to-Service.

**Enlace:** [Sesión 19](conversations/sesion-19.md)

**Decisión:** Se validaron los flujos de gestión de preguntas. Se decidió reforzar la arquitectura desacoplada obligando a que cualquier interacción pase por el Servicio del módulo destino. Se acordó finalizar el diseño hoy para comenzar la implementación integral en la próxima sesión.

## Sesión 20: [04/06/2026][17:00] Implementación CRUD Módulo Asignaturas

**Prompt:** Implementación modular del CRUD de asignaturas (crear, editar, eliminar) incluyendo la vinculación con Grado y la corrección de errores de seguridad y compilación.

**Resultado:**
- **Backend**: Implementación de `crearAsignatura`, `obtenerAsignatura`, `actualizarAsignatura` y `eliminarAsignatura` en `AsignaturaService`. 
- **Controller**: Endpoint `POST`, `GET`, `PUT`, `DELETE` en `AsignaturaController` con seguridad `ROLE_DOCENTE`.
- **Frontend**: Creación de `AsignaturaCreate.tsx` y `AsignaturaEdit.tsx`. Actualización de `AsignaturaList.tsx` para incluir navegación y borrado con confirmación.
- **Correcciones**: Corrección de errores de compilación (`DataInitializer`, `AsignaturaRepository`) y de seguridad (403 Forbidden). Resolución de errores de sintaxis en `App.tsx` y `AsignaturaList.tsx`.

**Enlace:** [Sesión 20](conversations/sesion-20.md)

**Decisión:** Se ha implementado el CRUD completo de asignaturas con validaciones de integridad referencial. Se ajustaron los permisos al rol `ROLE_DOCENTE`, el agente IA confundía la asignacion de permisos y se los asignaba a `DOCENTE`, rol que no existe. Se tuvieron que hacer varias correcciones porque la IA no agregaba el import de los metodos correspondentes y de vez en cuando duplicaba código.

## Sesión 21: [05/06/2026][12:30] Implementación CRUD Módulo Alumnos y Refactor DNI

**Prompt:** Implementación del CRUD de alumnos (crear, editar, eliminar) incluyendo la refactorización de 'niu' a 'dni' según el diagrama de dominio.

**Resultado:**
- **Backend**: Implementación de crearAlumno, obtenerAlumno, actualizarAlumno y eliminarAlumno en AlumnoService. Endpoint POST, GET, PUT, DELETE en AlumnoController.
- **Frontend**: Creación de AlumnoCreate.tsx y AlumnoEdit.tsx. Actualización de AlumnoList.tsx con navegación y borrado con confirmación.
- **Refactorización**: Cambio de 'niu' a 'dni' en Entidad, DTO, Servicios y Frontend.
- **Correcciones**: Resolución de problemas de migración de base de datos (grado_id NOT NULL) y depuración de errores 403.

**Enlace:** [Sesión 21](conversations/sesion-21.md)

**Decisión:** Se corrigió el formato de identificación de alumno de 'niu' a 'dni' o 'nie' para mantener consistencia con el diagrama de dominio. Se validó el formato de DNI/NIE tanto en backend como en frontend. Se completó el CRUD de alumnos con validación de unicidad de DNI.

## Sesión 22: [05/06/2026][15:23] Diseño del Módulo de Gestión de Preguntas y Respuestas (CRUD)

**Prompt:** Diseño de los casos de uso para la gestión de preguntas y respuestas (crear, editar, eliminar, ver), siguiendo la metodología de diseño estándar y alineando con los diagramas de secuencia del proyecto.

**Resultado:**
- **Diseño**: Creación de documentación (README.md) y diagramas de secuencia (.puml) para verRespuestas, crearRespuesta, editarRespuesta y eliminarRespuesta.
- **Arquitectura**: Estandarización de patrones MVC, aplicación del patrón 'El Gordo' para ediciones y 'El Delgado' para creaciones. Se estableció el estándar Service-to-Service para validación de autoría.
- **Documentación**: Ajuste de los diagramas para cumplir con los estándares de participantes y estilo del proyecto.

**Enlace:** [Sesión 22](conversations/sesion-22.md)

**Decisión:** Se aceptó el diseño completo para el CRUD del módulo de respuestas, garantizando la seguridad mediante validación de pertenencia en la capa de servicio y asegurando la integridad referencial en todas las operaciones.

## Sesión 23: [05/06/2026][18:15] Implementación CRUD Módulo Preguntas y Gestión Dual de Respuestas

**Prompt:** Implementación del CRUD de preguntas (crear, editar, eliminar) y sistema de gestión de respuestas (integral y granular).

**Resultado:**
- **Backend**: Implementación de PreguntaService, PreguntaController, RespuestaService y RespuestaController. Soporte para composición automática (orphanRemoval) y endpoints granulares.
- **Frontend**: Creación de PreguntaCreate.tsx, PreguntaEdit.tsx y RespuestaEdit.tsx. Integración de navegación contextual entre preguntas y sus respuestas.
- **Arquitectura**: Se ha aplicado el patrón 'El Gordo' para la gestión de la pregunta completa y se ha habilitado un flujo secundario para la edición individual de opciones.
- **Correcciones**: Resolución de errores de compilación en DataInitializer (vinculación con Asignatura) y limpieza de avisos de React en formularios.

**Enlace:** [Sesión 23](conversations/sesion-23.md)

**Decisión:** Se ha completado el módulo de Preguntas y Respuestas. Se decidió mantener ambos enfoques de gestión: Integral (dentro de la pregunta para mayor rapidez) y Granular (vista independiente para ediciones específicas), garantizando la flexibilidad del docente y la integridad de los datos en PostgreSQL.

## Sesión 24: [06/06/2026][10:32] Diseño Detallado de Generar Exámenes y Cancelar Generación

**Prompt:** Diseño de los casos de uso `generarExamenes` (considerando la complejidad de múltiples grados y tipos heredada de IdSw1) y `cancelarGeneracion`.

**Resultado:**
- **Diseño**: Generación de documentación (`README.md`) y diagramas de secuencia (`.puml`) detallados para ambos casos de uso.
- **Arquitectura Efímera**: Implementación de un sistema de borradores basado en `HttpSession` para evitar la persistencia prematura en la base de datos.
- **Complejidad IdSw1**: El diseño de generación ahora soporta configuraciones específicas por grado (diferentes tipos, número de exámenes y proporciones de dificultad) dentro de una misma asignatura.
- **Flujo de Salida**: Diseño de un endpoint de cancelación que limpia los borradores de la sesión de forma atómica.

**Enlace:** [Sesión 24](conversations/sesion-24.md)

**Decisión:** Se corrigió el diseño de generarExamenes para que siguiera la lógica propuesta en el proyecto de IdSw1 y que siga las prioridades proporcionadas para `JORGESTOR`. Se cambiaron los valores necesarios para la creación de exámenes, antes se creaba un examen con preguntas aleatorias, se corrigió para que las preguntas fuesen aleatorias pero dependan del grado y la dificultad asignados.

## Sesión 25: [06/06/2026][13:30] Implementación de Generación y Cancelación de Exámenes

**Prompt:** Implementación de la lógica de negocio y UI para `generarExamenes` (incluyendo configuración multi-grado) y `cancelarGeneracion`.

**Resultado:**
- **Backend**: Implementación de `ExamenService` con algoritmo de selección estratificado por dificultad y `ExamenSessionService` para gestión efímera de borradores.
- **Frontend**: Formulario dinámico en `GenerarExamenes.tsx` con configuración configurable por grado y validación de integridad.
- **Integración**: Corrección de errores de seguridad (CORS/Auth) y lógica de selección de preguntas robustecida contra casos de stock insuficiente.
- **Cancelación**: Endpoint de borrado de sesión (`/api/examenes/generar/cancelar`) integrado en la UI.

**Enlace:** [Sesión 25](conversations/sesion-25.md)

**Decisión:** Se ha finalizado la implementación de la funcionalidad central de generación de exámenes, respetando los principios de diseño de JORGESTOR y asegurando la consistencia entre el diseño de la fase de análisis y la implementación técnica final.
