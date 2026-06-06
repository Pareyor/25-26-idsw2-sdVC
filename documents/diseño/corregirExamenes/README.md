# Diseño de Caso de Uso: Corregir Exámenes

## 1. Descripción
Este caso de uso permite al docente realizar la corrección masiva de exámenes escaneados en formato PDF. El sistema utiliza una lógica simplificada para asignar una calificación aleatoria por cada página del PDF detectada, asumiendo una correspondencia de una página por alumno.

## 2. Actores
- **Docente**: Usuario que sube el archivo PDF y recibe las calificaciones generadas.

## 3. Patrones Aplicados
- **Service-to-Service**: El `ExamenController` delega la lógica de procesamiento al `CorreccionService`.
- **Simplificación (Mock Logic)**: Implementación de lógica de calificación aleatoria para fases tempranas de desarrollo.

## 4. Participantes

### Backend
- **ExamenController**: Endpoint `POST /api/examenes/corregir`. Recibe el archivo multipart.
- **CorreccionService**: Orquesta el procesamiento del archivo: cuenta páginas y asigna notas mediante lógica aleatoria.

### Frontend
- **CorregirExamenesView (React)**: Interfaz para subir el archivo PDF y visualizar los resultados devueltos por el backend.

## 5. Lógica de Control
1. El docente sube el PDF de exámenes resueltos.
2. El sistema valida el formato del archivo.
3. El `CorreccionService` cuenta el número de páginas.
4. Se itera sobre cada página generando una nota aleatoria entre 1 y 10.
5. Se devuelven los resultados al frontend para su visualización.

## 6. Diagrama de Secuencia
![Diagrama de Secuencia](../../../modelosUML/diseño/corregirExamenes/corregirExamenes.puml)
