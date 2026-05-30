# Documento de Arquitectura y Decisiones Tecnológicas - Jorgestor

Este documento define los cimientos técnicos del sistema **Jorgestor**, asegurando la coherencia entre el análisis, el diseño y la implementación final.

## 1. Stack Tecnológico Seleccionado

Se ha optado por una arquitectura de **Single Page Application (SPA)** con una **API REST**, priorizando la separación de responsabilidades, la mantenibilidad y el rigor académico de IDSW2.

### Backend: Java + Spring Boot
- **Framework:** Spring Boot 3.x.
- **Justificación:** Ecosistema robusto, inyección de dependencias (IoC), manejo avanzado de persistencia con Spring Data JPA y seguridad integral con Spring Security.
- **Rol:** Proveedor de servicios REST, orquestador de lógica de negocio y guardián de la integridad de los datos.

### Frontend: React + TypeScript
- **Framework:** React 18+.
- **Lenguaje:** TypeScript (Tipado estricto).
- **Estilos:** Tailwind CSS.
- **Justificación:** Tailwind permite un diseño moderno, altamente personalizable y eficiente mediante clases de utilidad, eliminando la necesidad de archivos CSS extensos y facilitando la consistencia visual.
- **Rol:** Interfaz de usuario reactiva, gestión de estado en cliente y consumo de la API REST.

### Base de Datos: PostgreSQL + Docker
- **Motor:** PostgreSQL (Relacional).
- **Infraestructura:** Contenedores Docker.
- **Justificación:** El uso de Docker asegura que el entorno de base de datos sea idéntico para todos los desarrolladores y en cualquier máquina, facilitando el despliegue y cumpliendo con estándares profesionales de "arranque inmediato".
- **ORM:** Hibernate (vía Spring Data JPA).

---

## 2. Decisiones de Diseño Globales

### Comunicación Cliente-Servidor
- **Protocolo:** HTTPS / JSON.
- **Estilo Arquitectónico:** RESTful.
- **Autenticación:** JWT (JSON Web Tokens) para stateless sessions, permitiendo escalabilidad y desacoplamiento.

### Gestión de Errores
- El backend proporcionará códigos de estado HTTP estandarizados (200, 201, 400, 401, 403, 404, 500) junto con un cuerpo de error descriptivo para que el frontend pueda informar correctamente al usuario.

### Organización del Código (Sugerida)
- **Backend:** Organizado por capas (Controller, Service, Repository, Entity, DTO).
- **Frontend:** Organizado por componentes funcionales, hooks personalizados para lógica de API y servicios de comunicación.

---

## 3. Hoja de Ruta de Diseño
Con este stack definido, cada Caso de Uso se diseñará siguiendo este flujo de interacción:
1. **Frontend (React/TS)** capta la intención del usuario.
2. **Controlador (Spring)** valida la petición.
3. **Servicio (Spring)** ejecuta la lógica de negocio.
4. **Repositorio (JPA)** interactúa con **PostgreSQL**.
