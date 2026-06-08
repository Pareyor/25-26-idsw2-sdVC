# Sesiones de VibeCoding / idsw2 / gII · uneatlantico / [**JORGESTOR**](https://github.com/martinlopez7/25-26-IdSw1-SdR)

---
### 📂 Navegación del Repositorio
[**🔍 Análisis**](documents/analisis/README.md) | [**🎨 Diseño**](documents/diseño/README.md) | [**💻 Desarrollo**](src) | [**📜 Log**](conversation-log.md)
---

## 🚀 Presentación del Sistema

**Jorgestor** es la evolución tecnológica del proyecto iniciado en *Ingeniería de Software 1 (IdSw1)*, ahora plenamente implementado como una plataforma web moderna. Su objetivo principal es optimizar el ciclo de evaluación académica, permitiendo a los docentes gestionar de forma integral el banco de preguntas y la generación de exámenes personalizados.

### 🎯 Definición del Proyecto
> [!IMPORTANT]
> Puedes consultar la definición rápida del sistema en: **[📄 ¿QUÉ HACE JORGESTOR?](QUE_HACE.md)**

### 🛠️ Funcionalidades Clave (Herencia IdSw1)

- **Gestión Multi-Docente (Aislamiento):** Sistema robusto de autenticación y autorización donde cada docente gestiona su propio ecosistema de alumnos, asignaturas y preguntas de forma aislada.
- **Banco de Preguntas Inteligente:** Creación y mantenimiento de una batería de preguntas categorizadas por tema, dificultad (Fácil, Medio, Difícil) y asignatura, incluyendo gestión granular de respuestas.
- **Generación de Exámenes Estratificada:** Algoritmo avanzado que genera exámenes aleatorios pero controlados, permitiendo configurar proporciones de dificultad y cantidad de preguntas por cada grado asociado a la asignatura.
- **Ciclo de Vida del Examen:** Soporta el flujo completo desde el borrador efímero en sesión, la asignación persistente a alumnos, hasta la corrección automatizada y almacenamiento de notas.
- **Interoperabilidad:** Módulo de configuración global que permite exportar e importar todo el ecosistema de datos en formato JSON, facilitando la portabilidad y copias de seguridad.

### 🏗️ Arquitectura y Stack
El sistema sigue los estándares de *IdSw2*, utilizando una arquitectura desacoplada:
- **Backend:** Spring Boot (Java) con PostgreSQL.
- **Frontend:** React (TypeScript) con Tailwind CSS.
- **Infraestructura:** Docker para la gestión del entorno de datos.

---
Construcción de un sistema con AI. Todo en el repositorio. [#2Think](2Think.md)
