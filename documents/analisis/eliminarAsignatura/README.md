# Análisis: eliminarAsignatura()

## Descripción del Caso de Uso
El docente solicita la eliminación permanente de una asignatura del sistema. Se presenta un diálogo de confirmación con los datos clave de la asignatura y una advertencia sobre la irreversibilidad de la acción.

## Modelos UML

### Diagrama de Colaboración
![Diagrama de Colaboración](colaboracion.puml)

### Diagrama de Secuencia
![Diagrama de Secuencia](secuencia.puml)

## Componentes MVC

### Vista
- **ConfirmacionEliminarAsignaturaDialog**: Diálogo que muestra los detalles de la asignatura (Código, Nombre, Curso, Batería) y solicita confirmación final.

### Controlador
- **AsignaturaController**: Gestiona la lógica de eliminación y coordina con el repositorio.

### Modelo (Entidad)
- **Asignatura**: La entidad que será eliminada.

### Repositorio
- **AsignaturaRepository**: Interfaz para la eliminación física del registro en el sistema de persistencia.
