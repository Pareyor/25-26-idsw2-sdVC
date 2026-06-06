# Diseño de Caso de Uso: Exportar Configuración Global (UC26)

## 1. Descripción
Este caso de uso es el único punto de entrada para la exportación masiva de la configuración del sistema. Actúa como **Agregador**, recolectando el estado actual de todas las entidades (Grados, Asignaturas, Alumnos, Preguntas) y serializándolo en un archivo JSON global.

## 2. Actores
- **Docente**: Usuario que solicita la exportación global.

## 3. Patrones Aplicados
- **Orchestrator**: El `ConfiguracionService` coordina la recolección de datos de todos los módulos.
- **Data Aggregation**: Crea una única vista del estado completo del docente para su exportación.

## 4. Participantes

### Backend
- **ConfiguracionController**: Endpoint `GET /api/configuracion/exportar`.
- **ConfiguracionService**: Orquesta la obtención de datos de los servicios específicos (`GradoService`, `AsignaturaService`, `AlumnoService`, `PreguntaService`).
- **ExportadorService**: Convierte el objeto agregado a formato JSON.

### Frontend
- **ExportarConfiguracionView (React)**: Interfaz única para solicitar la exportación.

## 5. Lógica de Control
1. El docente solicita la exportación.
2. `ConfiguracionService` recolecta los datos llamando a los métodos `obtenerTodo()` de cada servicio.
3. Se construye un objeto global.
4. `ExportadorService` serializa a JSON.
5. Se entrega el archivo.

## 6. Diagrama de Secuencia
![Diagrama de Secuencia](../../../modelosUML/diseño/importarConfiguracionGlobal/exportarConfiguracionGlobal.puml)
