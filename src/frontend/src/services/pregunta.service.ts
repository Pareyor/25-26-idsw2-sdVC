import axios from 'axios';
import type { Pregunta } from '../types/pregunta';

const API_URL = 'http://localhost:8080/api/preguntas';

const getAuthHeader = () => {
  const userStr = localStorage.getItem('user');
  const token = userStr ? JSON.parse(userStr).token : null;
  return { Authorization: `Bearer ${token}` };
};

export const getPreguntas = () => {
  return axios.get<Pregunta[]>(API_URL, {
    headers: getAuthHeader()
  });
};

export const createPregunta = (pregunta: Omit<Pregunta, 'id'>) => {
  return axios.post<Pregunta>(API_URL, pregunta, {
    headers: getAuthHeader()
  });
};
