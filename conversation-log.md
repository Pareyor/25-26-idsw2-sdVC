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

## Sesión 26: [06/06/2026][15:32] Limpieza de Documentación de Análisis (Abstractos). Diseño de asignarExamenes

**Prompt:** Diseño del caso de uso de asignarExamenes y eliminación de los archivos de análisis (diagramas puml y documentos) de los casos de uso abstractos de importación y exportación, para mantener la coherencia con el diseño centralizado.

**Resultado:**
- **AsignarExamenes (UC29)**: Diseño de la lógica de persistencia efímera a persistente, abstrayendo la base de datos a una capa de repositorio.
- **Limpieza de Repositorio**: Eliminación de directorios y archivos de análisis (`puml`, `README.md`) para `importarAlumnos`, `importarAsignaturas`, `importarGrados`, `importarPreguntas`, `exportarAlumnos`, `exportarAsignaturas`, `exportarGrados` y `exportarPreguntas`.
- **Coherencia Documental**: La estructura de documentación de análisis ahora refleja únicamente los casos de uso concretos y visibles, manteniendo la integridad del proyecto.

**Enlace:** [Sesión 26](conversations/sesion-26.md)

**Decisión:** Se ha consolidado toda la documentación para reflejar fielmente la arquitectura de JORGESTOR, asegurando que solo los casos de uso ejecutables y visibles tengan artefactos de análisis y diseño independientes.

## Sesión 27: [06/06/2026][17:02] Diseño de Corregir Exámenes y Finalización de Diseño

**Prompt:** Diseño de `corregirExamenes` (UC31) y validación de completitud del diseño funcional.

**Resultado:**
- **Diseño de CorregirExamenes (UC31)**: Diseño del flujo de corrección simplificada (conteo de páginas del PDF y generación aleatoria de notas), manteniendo la coherencia con la arquitectura de JORGESTOR.
- **Validación de Completitud**: Verificación final de que todos los casos de uso concretos están diseñados y documentados.
- **Limpieza de Repositorio**: Eliminación total de artefactos de análisis/diseño para los casos de uso abstractos, dejando el repositorio listo para la implementación.

**Enlace:** [Sesión 27](conversations/sesion-27.md)

**Decisión:** Se da por cerrada la etapa de diseño tras verificar que todos los casos de uso visibles (concretos) están correctamente modelados y documentados. El sistema está listo para pasar a la fase de implementación.

## Sesión 28: [06/06/2026][20:30] Implementación de Generar y Asignar Exámenes (UC28 & UC29)

**Prompt:** Implementación de las funcionalidades UC28 (Generar Exámenes) y UC29 (Asignar Exámenes) en el sistema Jorgestor, incluyendo los ajustes necesarios en el frontend y backend para la persistencia, validaciones de grado, y mejoras de usabilidad solicitadas por el usuario.

**Resultado:**
- **Implementación funcional**: Se completó el flujo de generación y asignación de exámenes con persistencia en base de datos.
- **Refactorización de persistencia**: Cambio de `HttpSession` a `ExamenBorrador` en BD para corregir errores de estado.
- **Diseño UI**: Estandarización a "Ethereal Light", paginación de listas, filtrado por nombre y selección de máximo por grado.
- **Correcciones técnicas**: Correcciones de compilación, gestión de contraseñas de docentes, ajuste de `ManyToMany` entre Asignatura y Grado, y mejora de la calidad de los datos de prueba (`DataInitializer`).

**Enlace:** [Sesión 28](conversations/sesion-28.md)

**Decisión:** Se da por finalizada la implementación de asignarExamenes y generarExamenes, verificando el flujo completo de principio a fin según los requisitos del usuario.

## Sesión 29: [07/06/2026][12:30] Implementación de Aislamiento de Datos y Nuevo Docente

**Prompt:** Implementar aislamiento de datos (multitenancy) para todos los módulos por profesor y crear un nuevo docente (Pablo Rey Ortiz) con datos poblados.

**Resultado:**
- **Aislamiento**: Se añadió profesor_id a las entidades necesarias y se actualizaron los repositorios/servicios para filtrar por docente logueado.
- **Nuevo Docente**: Inserción de 'Pablo Rey Ortiz' (72224668E) y actualización de DataInitializer para poblar entornos independientes para cada docente.
- **Correcciones**: Resolución de errores de compilación y errores de visibilidad de datos iniciales.

**Enlace:** [Sesión 29](conversations/sesion-29.md) 

**Decisión:** Se ha garantizado la privacidad de datos entre docentes. El sistema está listo para continuar con la gestión y corrección de exámenes en entornos aislados.

## Sesión 30: [07/06/2026][15:15] Refinamiento Final y Cierre

**Prompt:** Finalización de la implementación de corrección de exámenes, mejoras de navegación y limpieza de UI.

**Resultado:**
- **Corrección de Exámenes**: Implementación de corrección masiva (IA) y detalle persistido de respuestas.
- **Frontend**: Mejora de navegación (botones de retorno) y organización jerárquica de exámenes por asignatura.
- **UI/UX**: Añadido filtro por estado en la gestión de exámenes, visualización del docente logueado en el sidebar y limpieza de elementos gráficos no deseados.
- **Seguridad**: Refuerzo de RBAC (Admin/Docente) y resolución de errores de autorización (403).

**Enlace:** [Sesión 30](conversations/sesion-30.md)

**Decisión:** Se concluye la implementación de todas las funcionalidades solicitadas. El sistema es plenamente operativo.

## Sesión 31: [21:50] Implementación de importación/exportacion de configuración global y Resolución de Conflictos

**Prompt:** Reimplementar la importación/exportación de configuración JSON, corregir errores de autorización y renderizado, y resolver conflictos de integration con el repositorio remoto.

**Resultado:** 
- **Reimplementación**: Se recreó el módulo de importación/exportación asegurando que los datos importados se vinculen correctamente al docente que realiza la importación (mapeo de IDs). 
- **Idempotencia**: Se mejoró la lógica para que los datos antiguos del docente se limpien antes de importar los nuevos.
- **UI/UX**: Se corrigieron errores de visibilidad (CSS) y se ajustó el estilo del Dashboard para incluir botones de importación/exportación centrados y consistentes.
- **Integración**: Se resolvieron conflictos complejos de Git tras la sincronización, restaurando la estabilidad del código y garantizando que el sistema funciona correctamente.
- **DataInitializer**: Se ajustó la inicialización para que los nuevos docentes creados por el sistema no contengan datos de prueba, manteniendo el aislamiento.

**Enlace:** [Sesión 31](conversations/sesion-31.md)

**Decisión:** Se ha implementado con éxito la funcionalidad solicitada, garantizando la integridad de datos entre docentes y la estabilidad del proyecto. El sistema está sincronizado y plenamente operativo.

## Sesión 32: [07/06/2026][22:30] Enriquecimiento de Documentación con Diagramas

**Prompt:** Enriquecer los README de análisis y diseño con las imágenes de cada caso de uso analizado/diseñado.

**Resultado:**
- **Documentación**: Actualización de `documents/analisis/README.md` y `documents/diseño/README.md`.
- **Integración Visual**: Inclusión de diagramas de colaboración (Análisis) y diagramas de secuencia (Diseño) para todos los casos de uso operativos.
- **Relatividad**: Uso de rutas relativas para asegurar la visibilidad en el portal del repositorio.

**Decisión:** Se ha completado la documentación visual del proyecto, facilitando la comprensión de los flujos funcionales y técnicos de Jorgestor.

