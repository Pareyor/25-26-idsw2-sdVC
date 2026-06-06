# Diseño de Caso de Uso: Importar Preguntas

## 1. Descripción
Este caso de uso permite al docente cargar masivamente un conjunto de preguntas desde un archivo externo (ej. CSV o JSON) para asociarlas automáticamente a una asignatura existente.

## 2. Actores
- **Docente**: Usuario que sube el archivo de importación.

## 3. Patrones Aplicados
- **Service-to-Service**: `PreguntaController` delega el parseo del archivo a un `ImportadorService` y la lógica de creación a `PreguntaService`.
- **Strategy Pattern (implícito)**: El `ImportadorService` puede adaptarse según el formato de archivo (CSV, JSON).

## 4. Participantes

### Backend
- **PreguntaController**: Endpoint `POST /api/preguntas/importar`. Recibe el archivo y la `asignaturaId`.
- **ImportadorService**: Responsable de leer, parsear y convertir el archivo en una lista de objetos `PreguntaDTO`.
- **PreguntaService**: Reutiliza la lógica de `crearPregunta` para validar e insertar cada pregunta en la base de datos.
- **Repository**: Capa de persistencia.

### Frontend
- **ImportarPreguntasView (React)**: Interfaz para seleccionar archivo y asignatura destino.

## 5. Lógica de Control
1. El docente selecciona el archivo y la asignatura.
2. El archivo se envía al backend.
3. El `ImportadorService` extrae la información.
4. `PreguntaController` valida la lista.
5. `PreguntaService` persiste las preguntas.
6. Se informa al docente del éxito o errores encontrados.

## 6. Diagrama de Secuencia
![Diagrama de Secuencia](../../../modelosUML/diseño/importarPreguntas/importarPreguntas.puml)
