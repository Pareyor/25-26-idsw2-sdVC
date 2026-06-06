# Diseño de Caso de Uso: Importar Alumnos

## 1. Descripción
Este caso de uso permite al docente cargar masivamente información de alumnos desde un archivo externo (ej. CSV o JSON) para asociarlos automáticamente a un grado específico del sistema.

## 2. Actores
- **Docente**: Usuario que sube el archivo de importación.

## 3. Patrones Aplicados
- **Service-to-Service**: `AlumnoController` coordina con el `ImportadorService` para el procesamiento y con el `AlumnoService` para la persistencia.
- **Data Integrity**: Validaciones específicas sobre el formato de DNI/NIE de los alumnos durante la importación.

## 4. Participantes

### Backend
- **AlumnoController**: Endpoint `POST /api/alumnos/importar`. Recibe el archivo y el `gradoId`.
- **ImportadorService**: Responsable de leer y convertir el archivo en una lista de `AlumnoDTO`.
- **AlumnoService**: Valida la existencia previa de alumnos (por DNI/NIE) y persiste los nuevos registros.
- **Repository**: Capa de persistencia.

### Frontend
- **ImportarAlumnosView (React)**: Interfaz para seleccionar archivo y grado destino.

## 5. Lógica de Control
1. El docente selecciona el archivo y el grado.
2. El archivo se envía al backend.
3. El `ImportadorService` parsea la información.
4. `AlumnoController` valida la lista.
5. `AlumnoService` persiste los alumnos, evitando duplicados.
6. Se informa al docente del resultado.

## 6. Diagrama de Secuencia
![Diagrama de Secuencia](../../../modelosUML/diseño/importarAlumnos/importarAlumnos.puml)
