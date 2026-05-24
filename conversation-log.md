# Registro de Conversación - Jorgestor (IDSW2)

## Sesión 1: [20 de mayo de 2026][17:38]

### Resumen de actividad:
- **Arranque del proyecto**: Análisis de requerimientos legados de Jorgestor (Ingeniería de Software 1).
- **Definición técnica**: Creación de [`QUE_HACE.md`](QUE_HACE.md).
- **Estructura de archivos**: Inicialización de la carpeta de trabajo [`25-26-idsw2-sdVC`](https://github.com/Pareyor/25-26-idsw2-sdVC).
- **Documentación**: Ajuste de [`README.md`](README.md) para reflejar el enfoque en análisis, diseño e implementación de Jorgestor.

## Sesión 2: [21/05/2026][19:22] Análisis de los 5 primeros casos de uso (MVC)

**Prompt:** Análisis de los 5 primeros casos de uso de IdSw1-SdR basándose en el modelo de pySigHor, guardando modelos UML en `modelosUML/analisis/` y documentación en `documents/analisis/`, desglosando las acciones de Cargar, Corregir y Cancelar para el caso de `corregirExamenes()`.

**Resultado:** 
- Generación de análisis MVC (Vista, Control, Entidad) y diagramas de colaboración PlantUML para los casos de uso: `corregirExamenes()`, `generarExamenes()`, `importarConfiguracionGlobal()`, `exportarConfiguracionGlobal()` e `importarAlumnos()`.
- Estructuración de la documentación técnica en `documents/analisis/` y los modelos en `modelosUML/analisis/`.
- Refinamiento del análisis de `corregirExamenes()` para incluir explícitamente las responsabilidades de carga, corrección y cancelación.

**Enlace:** [Conversación Sesión 2](conversations/sesion-21-05-26.md)

**Decisión:** Se han aceptado los análisis de exportarConfiguracionGlobal, importarConfiguracionGlobal, importarAlumnos y generarExamenes. Se ha modificado el análisis de corregirExamenes que no tenía en cuenta la función de cargar exámenes, además me lo relacionaba con el apartado de reconocimiento de IA que, como hablamos en clase, debemos dejarlo como un factor externo al sistema.

## Sesión 3: [22/05/2026][11:45] Análisis de los casos de uso 6-10 y refinamiento por prototipos

**Prompt:** Análisis de los siguientes 5 casos de uso del priorizado (importarPreguntas, exportarAlumnos, exportarPreguntas, asignarExamenes, crearPregunta) tomando como ejemplo pySigHor y validando contra sus prototipos.

**Resultado:**
- Generación de diagramas de colaboración MVC y documentación README para los 5 casos de uso mencionados.
- Refinamiento de `asignarExamenes()` para integrar la búsqueda de alumnos y generación de claves alfanuméricas detectadas en el prototipo.
- Corrección del diagrama de `crearPregunta()` para vincular correctamente la colaboración `CompletarGestion`.

**Enlace:** [Conversación Sesión 3](conversations/sesion-22-05-26.md)

**Decisión:** Se aceptaron los análisis de `importarPreguntas`, `exportarAlumnos` (abstracto) y `exportarPreguntas` (abstracto). Se modificó significativamente `asignarExamenes()` para alinearlo con las funcionalidades visuales del prototipo (búsqueda y claves). Se corrigió una inconsistencia visual en `crearPregunta()` donde `CompletarGestion` estaba definido pero no relacionado con la acción de cancelar.

## Sesión 4: [23/05/2026][10:15] Análisis de Casos de Uso 11-15 y Estandarización de Estilo (MVC + Secuencia)

**Prompt:** Análisis de los casos de uso 11-15 (editarPregunta, editarAsignatura, crearDocente, crearAlumno, editarDocente) siguiendo pySigHor y aplicación del estilo de diagramas de secuencia personalizado (ejemplo verPreguntas) a todo el bloque 1-15.

**Resultado:**
- Generación de análisis MVC completo para los casos 11-15, aplicando los patrones "El Gordo" (edición integral) y "El Delgado" (creación rápida C->U).
- Creación de diagramas de secuencia "punto medio" (exquisitos pero sintetizados) para los casos 1-15, integrando activaciones, instanciación de objetos y notas de interfaz.
- Estandarización de los 15 README.md de análisis bajo el nombre de proyecto "Jorgestor - Sistema de Gestión de Exámenes".

**Enlace:** [Conversación Sesión 4](conversations/sesion-23-05-26.md)

**Decisión:** Se ha modificado el análisis de casos de uso ya implementados (1-10) para corregir la forma en que hacía los diagramas de secuencia, se estableció un mismo formato para realizarlos, basandose en pySighor y amoldándose al proyecto JORGESTOR. Se modificó el caso de uso editarAsignatura() porque la IA no detectaba la colaboración con verPreguntas() y pasaba lo mismo en editarPregunta() que no detectaba la colaboración con verRespuestas(). Por se unificó el nombre del proyecto ya que el agente IA ponía siempre de base IdSw1.

## Sesión 5: [24/05/2026][12:45] Análisis de Casos de Uso 16-20 y Alineación con Prototipos y Estilo Visual

**Prompt:** Análisis de los casos de uso 16-20 (editarAlumno, crearGrado, crearAsignatura, editarGrado, verPreguntas) siguiendo el patrón MVC, validando contra sus prototipos y estandarizando el estilo visual (colores y estructura) basándose en el ejemplo `editarAsignatura`.

**Resultado:**
- Generación de análisis MVC completo y diagramas (Colaboración y Secuencia) para los casos 16-20.
- Aplicación del estilo visual estandarizado: colores específicos para capas MVC (`#CDEBA5`, `#629EF9`, `#b5bd68`, `#F2AC4E`) y uso de `rectangle` en colaboraciones.
- Inclusión de notas de interfaz detalladas en los diagramas de secuencia basadas en los wireframes de prototipado.
- Refinamiento de la lógica de creación (`crearGrado`, `crearAsignatura`) para incluir asociaciones iniciales y redirección automática.
- Actualización de los `README.md` de análisis documentando los patrones "El Gordo" y "El Delgado".

**Enlace:** [Conversación Sesión 5](conversations/sesion-24-05-26.md)

**Decisión:** Se aceptó el análisis inicial de los 5 casos de uso, pero se modificó posteriormente para cumplir con dos requisitos críticos: 1) la alineación con los prototipos visuales (se añadieron campos de selección de alumnos y grados en las creaciones) y 2) la estandarización estética total siguiendo el modelo de `editarAsignatura`. Se corrigió la falta de mención a la `BateriaDePreguntas` en `crearAsignatura` y se detalló el bucle de filtrado en `verPreguntas`.
