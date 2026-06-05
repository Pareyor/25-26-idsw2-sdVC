import axios from 'axios';
import type { Respuesta } from '../types/pregunta';

const API_URL = 'http://localhost:8080/api/respuestas';

const getAuthHeader = () => {
  const userStr = localStorage.getItem('user');
  const token = userStr ? JSON.parse(userStr).token : null;
  return { Authorization: `Bearer ${token}` };
};

export const getRespuestasPorPregunta = (preguntaId: number) => {
  return axios.get<Respuesta[]>(`${API_URL}/pregunta/${preguntaId}`, {
    headers: getAuthHeader()
  });
};

export const updateRespuesta = (id: number, respuesta: Respuesta) => {
  return axios.put<Respuesta>(`${API_URL}/${id}`, respuesta, {
    headers: getAuthHeader()
  });
};

export const deleteRespuesta = (id: number) => {
  return axios.delete(`${API_URL}/${id}`, {
    headers: getAuthHeader()
  });
};
