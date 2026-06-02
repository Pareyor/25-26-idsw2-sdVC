import axios from 'axios';

const API_URL = 'http://localhost:8080/api/preguntas';

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

export interface Pregunta {
  id: number;
  enunciado: string;
  tema: Tema;
  dificultad: Dificultad;
}

export const getPreguntas = () => {
  const userStr = localStorage.getItem('user');
  const token = userStr ? JSON.parse(userStr).token : null;
  return axios.get<Pregunta[]>(API_URL, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
};
