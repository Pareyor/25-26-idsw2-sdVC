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

