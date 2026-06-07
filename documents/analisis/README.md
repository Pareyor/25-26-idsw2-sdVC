# Análisis de Casos de Uso - Jorgestor

---
### 📂 Navegación del Repositorio
[**🏠 Inicio**](../../README.md) | [**🎨 Diseño**](../diseño) | [**💻 Desarrollo**](../../src)
---

Esta carpeta contiene la documentación detallada del análisis de los casos de uso del sistema **Jorgestor**. Cada subdirectorio corresponde a un caso de uso específico y contiene su análisis funcional.

## Diagramas de Colaboración (Análisis)

A continuación se presentan los diagramas de colaboración resultantes del análisis de cada caso de uso, organizados por módulos funcionales.

### 🔐 Gestión de Sesión
Permite el acceso seguro al sistema y la finalización de la jornada de trabajo.

| Inicio de Sesión | Cerrar Sesión |
| :---: | :---: |
| ![Iniciar Sesión](../../images/analisis/iniciarSesion/iniciarSesion.svg) | ![Cerrar Sesión](../../images/analisis/cerrarSesion/cerrarSesion.svg) |

---

### 📊 Dashboard y Navegación
Punto central de entrada tras el login que orquesta las opciones disponibles según el perfil.

| Completar Gestión |
| :---: |
| ![Completar Gestión](../../images/analisis/completarGestion/completarGestion.svg) |

---

### 🎓 Gestión de Grados
Administración de los niveles académicos o grupos de alumnos.

| Ver Grados | Crear Grado |
| :---: | :---: |
| ![Ver Grados](../../images/analisis/verGrados/verGrados.svg) | ![Crear Grado](../../images/analisis/crearGrado/crearGradoColaboracion.svg) |

| Editar Grado | Eliminar Grado |
| :---: | :---: |
| ![Editar Grado](../../images/analisis/editarGrado/editarGradoColaboracion.svg) | ![Eliminar Grado](../../images/analisis/eliminarGrado/eliminarGrado.svg) |

---

### 📚 Gestión de Asignaturas
Configuración de las materias impartidas y su vinculación con grados.

| Ver Asignaturas | Crear Asignatura |
| :---: | :---: |
| ![Ver Asignaturas](../../images/analisis/verAsignaturas/verAsignaturas.svg) | ![Crear Asignatura](../../images/analisis/crearAsignatura/crearAsignatura.svg) |

| Editar Asignatura | Eliminar Asignatura |
| :---: | :---: |
| ![Editar Asignatura](../../images/analisis/editarAsignatura/editarAsignaturaColaboracion.svg) | ![Eliminar Asignatura](../../images/analisis/eliminarAsignatura/eliminarAsignatura.svg) |

---

### 👥 Gestión de Alumnos
Mantenimiento de la base de datos de estudiantes.

| Ver Alumnos | Crear Alumno |
| :---: | :---: |
| ![Ver Alumnos](../../images/analisis/verAlumnos/verAlumnos.svg) | ![Crear Alumno](../../images/analisis/crearAlumno/crearAlumnoColaboracion.svg) |

| Editar Alumno | Eliminar Alumno |
| :---: | :---: |
| ![Editar Alumno](../../images/analisis/editarAlumno/editarAlumnoColaboracion.svg) | ![Eliminar Alumno](../../images/analisis/eliminarAlumno/eliminarAlumno.svg) |

---

### ❓ Gestión de Preguntas
Construcción del banco de ítems de evaluación.

| Ver Preguntas | Crear Pregunta |
| :---: | :---: |
| ![Ver Preguntas](../../images/analisis/verPreguntas/verPreguntasColaboracion.svg) | ![Crear Pregunta](../../images/analisis/crearPregunta/crearPregunta.svg) |

| Editar Pregunta | Eliminar Pregunta |
| :---: | :---: |
| ![Editar Pregunta](../../images/analisis/editarPregunta/editarPreguntaColaboracion.svg) | ![Eliminar Pregunta](../../images/analisis/eliminarPregunta/eliminarPreguntaColaboracion.svg) |

---

### 📝 Gestión de Respuestas
Definición de las opciones y soluciones para cada pregunta.

| Ver Respuestas | Crear Respuesta |
| :---: | :---: |
| ![Ver Respuestas](../../images/analisis/verRespuestas/verRespuestasColaboracion.svg) | ![Crear Respuesta](../../images/analisis/crearRespuesta/crearRespuesta.svg) |

| Editar Respuesta | Eliminar Respuesta |
| :---: | :---: |
| ![Editar Respuesta](../../images/analisis/editarRespuesta/editarRespuesta.svg) | ![Eliminar Respuesta](../../images/analisis/eliminarRespuesta/eliminarRespuesta.svg) |

---

### 📝 Gestión de Exámenes
Núcleo del sistema para la generación, asignación y corrección.

| Generar Exámenes | Cancelar Generación |
| :---: | :---: |
| ![Generar Exámenes](../../images/analisis/generarExamenes/generarExamenes.svg) | ![Cancelar Generación](../../images/analisis/cancelarGeneracion/cancelarGeneracion.svg) |

| Asignar Exámenes | Corregir Exámenes |
| :---: | :---: |
| ![Asignar Exámenes](../../images/analisis/asignarExamenes/asignarExamenes.svg) | ![Corregir Exámenes](../../images/analisis/corregirExamenes/corregirExamenes.svg) |

---

### ⚙️ Configuración y Sistema
Opciones avanzadas y administración de usuarios (Docentes).

| Ver Docentes | Crear Docente |
| :---: | :---: |
| ![Ver Docentes](../../images/analisis/verDocentes/verDocentes.svg) | ![Crear Docente](../../images/analisis/crearDocente/crearDocenteColaboracion.svg) |

| Editar Docente | Eliminar Docente |
| :---: | :---: |
| ![Editar Docente](../../images/analisis/editarDocente/editarDocenteColaboracion.svg) | ![Eliminar Docente](../../images/analisis/eliminarDocente/eliminarDocente.svg) |

| Importar Configuración | Exportar Configuración |
| :---: | :---: |
| ![Importar](../../images/analisis/importarConfiguracionGlobal/importarConfiguracionGlobal.svg) | ![Exportar](../../images/analisis/exportarConfiguracionGlobal/exportarConfiguracionGlobal.svg) |
