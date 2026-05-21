# PROTOCOLOS_IA.md - Guía para el agente IA
---
Este archivo sirve para automatizar al agente IA para que obtenga el contexto o actualice el conversation-log simplemente con escribir una palabra.
## INICIALIZACIÓN 
Para que la IA tenga contexto de cual es el punto actual del proyecto cuando se escriba "Inicio" el agente debe leer y analizar los siguientes archivos:
(Añadir dirección a archivos esenciales).
## FINALIZACIÓN
Cuando se escriba "fin" el agente debe actualizar el archivo `conversation-log.md` con la reciente sesión de la siguiente forma:

## [HH:MM] Título breve de lo que se pidió

**Prompt:** lo que le dijo al AI (textual o resumido fielmente)

**Resultado:** lo que produjo

**Decisión:** qué aceptó, qué rechazó, qué modificó, y por qué