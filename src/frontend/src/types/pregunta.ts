export enum Tema {
  GENERAL = 'GENERAL',
  TEORIA = 'TEORIA',
  PRACTICA = 'PRACTICA',
  DISENO = 'DISENO',
  IMPLEMENTACION = 'IMPLEMENTACION'
}

export enum Dificultad {
  FACIL = 'FACIL',
  MEDIO = 'MEDIO',
  DIFICIL = 'DIFICIL'
}

export interface Respuesta {
  id?: number;
  opcion: string;
  esCorrecta: boolean;
}

export interface Pregunta {
  id: number;
  enunciado: string;
  tema: string;
  dificultad: Dificultad;
  asignaturaId: number;
  respuestas: Respuesta[];
}
