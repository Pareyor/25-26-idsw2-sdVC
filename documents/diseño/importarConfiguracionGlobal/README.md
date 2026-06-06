# Diseño de Caso de Uso: Importar Configuración Global (UC27)

## 1. Descripción
Este caso de uso es el único punto de entrada para la importación masiva de datos en el sistema. Actúa como **Orquestador**, recibiendo un archivo JSON global y distribuyendo la carga de datos a los servicios de dominio correspondientes.

## 2. Actores
- **Docente**: Usuario que sube el archivo de configuración global.

## 3. Patrones Aplicados
- **Orchestrator**: El `ConfiguracionService` coordina la ejecución secuencial de la limpieza e importación de cada entidad.
- **Reset & Import**: Patrón para asegurar un estado limpio antes de la reconstrucción.

## 4. Participantes

### Backend
- **ConfiguracionController**: Endpoint `POST /api/configuracion/importar`.
- **ConfiguracionService**: Orquesta las llamadas a los servicios específicos de dominio (`GradoService`, `AsignaturaService`, `AlumnoService`, `PreguntaService`).

### Frontend
- **ImportarConfiguracionView (React)**: Interfaz única para la carga del archivo global.

## 5. Lógica de Control
1. El docente sube el archivo JSON.
2. `ConfiguracionService` ejecuta la fase de **RESET** (borrado en cascada) llamando a los métodos `eliminarPorDocente()` de cada servicio.
3. `ConfiguracionService` ejecuta la fase de **IMPORTACIÓN** llamando a los métodos `crear()` de cada servicio en orden de dependencia.
4. El sistema informa del éxito o errores.

## 6. Diagrama de Secuencia
![Diagrama de Secuencia](../../../modelosUML/diseño/importarConfiguracionGlobal/importarConfiguracionGlobal.puml)
