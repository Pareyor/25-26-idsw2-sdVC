# Diseño de Caso de Uso: Importar Asignaturas

## 1. Descripción
Este caso de uso permite al docente cargar masivamente información de asignaturas desde un archivo externo (ej. CSV o JSON).

## 2. Actores
- **Docente**: Usuario que sube el archivo de importación.

## 3. Patrones Aplicados
- **Service-to-Service**: `AsignaturaController` coordina con el `ImportadorService` para el procesamiento y con el `AsignaturaService` para la persistencia.

## 4. Participantes

### Backend
- **AsignaturaController**: Endpoint `POST /api/asignaturas/importar`. Recibe el archivo.
- **ImportadorService**: Responsable de leer y convertir el archivo en una lista de `AsignaturaDTO`.
- **AsignaturaService**: Valida la existencia previa de la asignatura (por código) y persiste los nuevos registros.
- **Repository**: Capa de persistencia.

### Frontend
- **ImportarAsignaturasView (React)**: Interfaz para seleccionar el archivo.

## 5. Lógica de Control
1. El docente selecciona el archivo.
2. El archivo se envía al backend.
3. El `ImportadorService` parsea la información.
4. `AsignaturaController` valida la lista.
5. `AsignaturaService` persiste las asignaturas, evitando duplicados por código.
6. Se informa al docente del resultado.

## 6. Diagrama de Secuencia
![Diagrama de Secuencia](../../../modelosUML/diseño/importarAsignaturas/importarAsignaturas.puml)
