# Análisis de Casos de Uso - Jorgestor

---
### 📂 Navegación del Repositorio
[**🏠 Inicio**](../../README.md) | [**🔍 Análisis**](README.md) | [**🎨 Diseño**](../diseño/README.md) | [**💻 Desarrollo**](../../src) | [**📜 Log**](../../conversation-log.md) | [**🗺️ Diagrama de Contexto**](../../archivosEsenciales/casos-de-uso/diagramasDeContexto/diagramaDeContextoDocente/diagramaContexto.puml)
---

Esta carpeta contiene la documentación detallada del análisis de los casos de uso del sistema **Jorgestor**. Cada subdirectorio corresponde a un caso de uso específico y contiene su análisis funcional.

## Diagramas de Colaboración (Análisis)

A continuación se presentan los diagramas de colaboración resultantes del análisis de cada caso de uso, organizados por módulos funcionales.

### 🔐 Gestión de Sesión
Permite el acceso seguro al sistema y la finalización de la jornada de trabajo.

| [Iniciar Sesión](iniciarSesion) | [Cerrar Sesión](cerrarSesion) |
| :---: | :---: |
| ![Iniciar Sesión](../../images/analisis/iniciarSesion/iniciarSesion.svg)<br>[📄 Código PUML](../../modelosUML/analisis/iniciarSesion/colaboracion.puml) | ![Cerrar Sesión](../../images/analisis/cerrarSesion/cerrarSesion.svg)<br>[📄 Código PUML](../../modelosUML/analisis/cerrarSesion/colaboracion.puml) |

---

### 📊 Dashboard y Navegación
Punto central de entrada tras el login que orquesta las opciones disponibles según el perfil.

| [Completar Gestión](completarGestion) |
| :---: |
| ![Completar Gestión](../../images/analisis/completarGestion/completarGestion.svg)<br>[📄 Código PUML](../../modelosUML/analisis/completarGestion/colaboracion.puml) |

---

### 🎓 Gestión de Grados
Administración de los niveles académicos o grupos de alumnos.

| [Ver Grados](verGrados) | [Crear Grado](crearGrado) |
| :---: | :---: |
| ![Ver Grados](../../images/analisis/verGrados/verGrados.svg)<br>[📄 Código PUML](../../modelosUML/analisis/verGrados/colaboracion.puml) | ![Crear Grado](../../images/analisis/crearGrado/crearGradoColaboracion.svg)<br>[📄 Código PUML](../../modelosUML/analisis/crearGrado/colaboracion.puml) |

| [Editar Grado](editarGrado) | [Eliminar Grado](eliminarGrado) |
| :---: | :---: |
| ![Editar Grado](../../images/analisis/editarGrado/editarGradoColaboracion.svg)<br>[📄 Código PUML](../../modelosUML/analisis/editarGrado/colaboracion.puml) | ![Eliminar Grado](../../images/analisis/eliminarGrado/eliminarGrado.svg)<br>[📄 Código PUML](../../modelosUML/analisis/eliminarGrado/colaboracion.puml) |

---

### 📚 Gestión de Asignaturas
Configuración de las materias impartidas y su vinculación con grados.

| [Ver Asignaturas](verAsignaturas) | [Crear Asignatura](crearAsignatura) |
| :---: | :---: |
| ![Ver Asignaturas](../../images/analisis/verAsignaturas/verAsignaturas.svg)<br>[📄 Código PUML](../../modelosUML/analisis/verAsignaturas/colaboracion.puml) | ![Crear Asignatura](../../images/analisis/crearAsignatura/crearAsignatura.svg)<br>[📄 Código PUML](../../modelosUML/analisis/crearAsignatura/colaboracion.puml) |

| [Editar Asignatura](editarAsignatura) | [Eliminar Asignatura](eliminarAsignatura) |
| :---: | :---: |
| ![Editar Asignatura](../../images/analisis/editarAsignatura/editarAsignaturaColaboracion.svg)<br>[📄 Código PUML](../../modelosUML/analisis/editarAsignatura/colaboracion.puml) | ![Eliminar Asignatura](../../images/analisis/eliminarAsignatura/eliminarAsignatura.svg)<br>[📄 Código PUML](../../modelosUML/analisis/eliminarAsignatura/colaboracion.puml) |

---

### 👥 Gestión de Alumnos
Mantenimiento de la base de datos de estudiantes.

| [Ver Alumnos](verAlumnos) | [Crear Alumno](crearAlumno) |
| :---: | :---: |
| ![Ver Alumnos](../../images/analisis/verAlumnos/verAlumnos.svg)<br>[📄 Código PUML](../../modelosUML/analisis/verAlumnos/colaboracion.puml) | ![Crear Alumno](../../images/analisis/crearAlumno/crearAlumnoColaboracion.svg)<br>[📄 Código PUML](../../modelosUML/analisis/crearAlumno/colaboracion.puml) |

| [Editar Alumno](editarAlumno) | [Eliminar Alumno](eliminarAlumno) |
| :---: | :---: |
| ![Editar Alumno](../../images/analisis/editarAlumno/editarAlumnoColaboracion.svg)<br>[📄 Código PUML](../../modelosUML/analisis/editarAlumno/colaboracion.puml) | ![Eliminar Alumno](../../images/analisis/eliminarAlumno/eliminarAlumno.svg)<br>[📄 Código PUML](../../modelosUML/analisis/eliminarAlumno/colaboracion.puml) |

---

### ❓ Gestión de Preguntas
Construcción del banco de ítems de evaluación.

| [Ver Preguntas](verPreguntas) | [Crear Pregunta](crearPregunta) |
| :---: | :---: |
| ![Ver Preguntas](../../images/analisis/verPreguntas/verPreguntasColaboracion.svg)<br>[📄 Código PUML](../../modelosUML/analisis/verPreguntas/colaboracion.puml) | ![Crear Pregunta](../../images/analisis/crearPregunta/crearPregunta.svg)<br>[📄 Código PUML](../../modelosUML/analisis/crearPregunta/colaboracion.puml) |

| [Editar Pregunta](editarPregunta) | [Eliminar Pregunta](eliminarPregunta) |
| :---: | :---: |
| ![Editar Pregunta](../../images/analisis/editarPregunta/editarPreguntaColaboracion.svg)<br>[📄 Código PUML](../../modelosUML/analisis/editarPregunta/colaboracion.puml) | ![Eliminar Pregunta](../../images/analisis/eliminarPregunta/eliminarPreguntaColaboracion.svg)<br>[📄 Código PUML](../../modelosUML/analisis/eliminarPregunta/colaboracion.puml) |

---

### 📝 Gestión de Respuestas
Definición de las opciones y soluciones para cada pregunta.

| [Ver Respuestas](verRespuestas) | [Crear Respuesta](crearRespuesta) |
| :---: | :---: |
| ![Ver Respuestas](../../images/analisis/verRespuestas/verRespuestasColaboracion.svg)<br>[📄 Código PUML](../../modelosUML/analisis/verRespuestas/colaboracion.puml) | ![Crear Respuesta](../../images/analisis/crearRespuesta/crearRespuesta.svg)<br>[📄 Código PUML](../../modelosUML/analisis/crearRespuesta/colaboracion.puml) |

| [Editar Respuesta](editarRespuesta) | [Eliminar Respuesta](eliminarRespuesta) |
| :---: | :---: |
| ![Editar Respuesta](../../images/analisis/editarRespuesta/editarRespuesta.svg)<br>[📄 Código PUML](../../modelosUML/analisis/editarRespuesta/colaboracion.puml) | ![Eliminar Respuesta](../../images/analisis/eliminarRespuesta/eliminarRespuesta.svg)<br>[📄 Código PUML](../../modelosUML/analisis/eliminarRespuesta/colaboracion.puml) |

---

### 📝 Gestión de Exámenes
Núcleo del sistema para la generación, asignación y corrección.

| [Generar Exámenes](generarExamenes) | [Cancelar Generación](cancelarGeneracion) |
| :---: | :---: |
| ![Generar Exámenes](../../images/analisis/generarExamenes/generarExamenes.svg)<br>[📄 Código PUML](../../modelosUML/analisis/generarExamenes/colaboracion.puml) | ![Cancelar Generación](../../images/analisis/cancelarGeneracion/cancelarGeneracion.svg)<br>[📄 Código PUML](../../modelosUML/analisis/cancelarGeneracion/colaboracion.puml) |

| [Asignar Exámenes](asignarExamenes) | [Corregir Exámenes](corregirExamenes) |
| :---: | :---: |
| ![Asignar Exámenes](../../images/analisis/asignarExamenes/asignarExamenes.svg)<br>[📄 Código PUML](../../modelosUML/analisis/asignarExamenes/colaboracion.puml) | ![Corregir Exámenes](../../images/analisis/corregirExamenes/corregirExamenes.svg)<br>[📄 Código PUML](../../modelosUML/analisis/corregirExamenes/colaboracion.puml) |

| [Ver Exámenes](verExamenes) | [Ver Examen](verExamen) |
| :---: | :---: |
| ![Ver Exámenes](../../images/analisis/verExamenes/verExamenes.svg)<br>[📄 Código PUML](../../modelosUML/analisis/verExamenes/colaboracion.puml) | ![Ver Examen](../../images/analisis/verExamen/verExamen.svg)<br>[📄 Código PUML](../../modelosUML/analisis/verExamen/colaboracion.puml) |

---

### ⚙️ Configuración y Sistema
Opciones avanzadas y administración de usuarios (Docentes).

| [Ver Docentes](verDocentes) | [Crear Docente](crearDocente) |
| :---: | :---: |
| ![Ver Docentes](../../images/analisis/verDocentes/verDocentes.svg)<br>[📄 Código PUML](../../modelosUML/analisis/verDocentes/colaboracion.puml) | ![Crear Docente](../../images/analisis/crearDocente/crearDocenteColaboracion.svg)<br>[📄 Código PUML](../../modelosUML/analisis/crearDocente/colaboracion.puml) |

| [Editar Docente](editarDocente) | [Eliminar Docente](eliminarDocente) |
| :---: | :---: |
| ![Editar Docente](../../images/analisis/editarDocente/editarDocenteColaboracion.svg)<br>[📄 Código PUML](../../modelosUML/analisis/editarDocente/colaboracion.puml) | ![Eliminar Docente](../../images/analisis/eliminarDocente/eliminarDocente.svg)<br>[📄 Código PUML](../../modelosUML/analisis/eliminarDocente/colaboracion.puml) |

| [Importar Configuración](importarConfiguracionGlobal) | [Exportar Configuración](exportarConfiguracionGlobal) |
| :---: | :---: |
| ![Importar](../../images/analisis/importarConfiguracionGlobal/importarConfiguracionGlobal.svg)<br>[📄 Código PUML](../../modelosUML/analisis/importarConfiguracionGlobal/colaboracion.puml) | ![Exportar](../../images/analisis/exportarConfiguracionGlobal/exportarConfiguracionGlobal.svg)<br>[📄 Código PUML](../../modelosUML/analisis/exportarConfiguracionGlobal/colaboracion.puml) |
