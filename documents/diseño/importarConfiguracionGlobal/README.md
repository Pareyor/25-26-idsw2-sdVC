# Diseño de Caso de Uso: Importar Configuración Global

## 1. Descripción
Este caso de uso actúa como **Orquestador**. Realiza una importación destructiva: primero limpia toda la información del docente (Reset) y luego reconstruye el sistema con la configuración global proporcionada en un archivo JSON (Import).

## 2. Actores
- **Docente**: Usuario que sube el archivo de configuración global.

## 3. Patrones Aplicados
- **Orchestrator**: El `ConfiguracionService` coordina la ejecución de las fases de limpieza e importación.
- **Reset & Import**: Patrón para asegurar que la nueva configuración global sea la única presente para el docente, evitando datos huérfanos o inconsistencias.

## 4. Participantes

### Backend
- **ConfiguracionController**: Endpoint `POST /api/configuracion/importar`.
- **ConfiguracionService**: Orquesta las llamadas secuenciales para limpiar (fase 1) y reconstruir (fase 2) los datos de cada entidad.

### Frontend
- **ImportarConfiguracionView (React)**: Interfaz para seleccionar el archivo y confirmar la destrucción de datos previos.

## 5. Lógica de Control
1. El docente sube el archivo y confirma la advertencia de destrucción de datos.
2. `ConfiguracionService` ejecuta la fase de **RESET** en orden inverso a las dependencias.
3. `ConfiguracionService` ejecuta la fase de **IMPORTACIÓN** en orden de dependencia.
4. Se garantiza la integridad de los datos.

## 6. Diagrama de Secuencia
![Diagrama de Secuencia](../../../modelosUML/diseño/importarConfiguracionGlobal/importarConfiguracionGlobal.puml)
