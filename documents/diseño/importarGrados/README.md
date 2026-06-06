# Diseño de Caso de Uso: Importar Grados

## 1. Descripción
Este caso de uso permite al docente cargar masivamente información de grados académicos desde un archivo externo (ej. CSV o JSON).

## 2. Actores
- **Docente**: Usuario que sube el archivo de importación.

## 3. Patrones Aplicados
- **Service-to-Service**: `GradoController` coordina con el `ImportadorService` para el procesamiento y con el `GradoService` para la persistencia.

## 4. Participantes

### Backend
- **GradoController**: Endpoint `POST /api/grados/importar`. Recibe el archivo.
- **ImportadorService**: Responsable de leer y convertir el archivo en una lista de `GradoDTO`.
- **GradoService**: Valida la existencia previa del grado (por código) y persiste los nuevos registros.
- **Repository**: Capa de persistencia.

### Frontend
- **ImportarGradosView (React)**: Interfaz para seleccionar el archivo.

## 5. Lógica de Control
1. El docente selecciona el archivo.
2. El archivo se envía al backend.
3. El `ImportadorService` parsea la información.
4. `GradoController` valida la lista.
5. `GradoService` persiste los grados, evitando duplicados por código.
6. Se informa al docente del resultado.

## 6. Diagrama de Secuencia
![Diagrama de Secuencia](../../../modelosUML/diseño/importarGrados/importarGrados.puml)
