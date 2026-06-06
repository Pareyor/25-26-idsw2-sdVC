# Diseño de Caso de Uso: Cancelar Generación

## 1. Descripción
Este caso de uso permite al docente descartar los exámenes generados en la fase de previsualización. Al ser una arquitectura efímera, la cancelación simplemente elimina los objetos almacenados temporalmente en la sesión del servidor, asegurando que no se persista ninguna información innecesaria en la base de datos.

## 2. Actores
- **Docente**: Usuario que decide no proceder con la asignación de los exámenes generados.

## 3. Patrones Aplicados
- **Session Cleanup**: Uso de la sesión para gestionar el ciclo de vida de datos volátiles.
- **RESTful DELETE**: Uso del método DELETE para representar la eliminación de un recurso temporal (borrador).

## 4. Participantes

### Backend
- **ExamenController**: Expone el endpoint `DELETE /api/examenes/generar/cancelar`.
- **ExamenSessionService**: Proporciona el método para invalidar los datos de la sesión.

### Frontend
- **ExamenPrevisualizacion (React)**: Interfaz que muestra los resultados temporales y ofrece la opción de cancelar.
- **ExamenService (TS)**: Realiza la llamada a la API de cancelación.

## 5. Lógica de Control
1. El sistema recibe la petición de cancelación.
2. Se accede a la `HttpSession` actual del docente.
3. Se elimina el atributo `EXAMENES_BORRADOR`.
4. El frontend redirige al docente al Dashboard principal.

## 6. Diagrama de Secuencia
![Diagrama de Secuencia](../../../modelosUML/diseño/cancelarGeneracion/cancelarGeneracion.puml)
