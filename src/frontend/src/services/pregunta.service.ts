import axios from 'axios';

const API_URL = 'http://localhost:8080/api/preguntas';

export interface PreguntaDTO {
  id?: number;
  enunciado: string;
  tipo: string;
  tema: string;
  dificultad: string;
  asignaturaId?: number;
}

const getAuthHeader = () => {
  const userStr = localStorage.getItem('user');
  const token = userStr ? JSON.parse(userStr).token : null;
  return { Authorization: `Bearer ${token}` };
};

export const getPreguntas = () => {
  return axios.get<PreguntaDTO[]>(API_URL, {
    headers: getAuthHeader()
  });
};

export const getPreguntasByAsignatura = (asignaturaId: number) => {
  return axios.get<PreguntaDTO[]>(`${API_URL}/asignatura/${asignaturaId}`, {
    headers: getAuthHeader()
  });
};

export const getPregunta = (id: number) => {
  return axios.get<PreguntaDTO>(`${API_URL}/${id}`, { 
    headers: getAuthHeader() 
  });
};

export const getTemasByAsignatura = (asignaturaId: number) => {
  return axios.get<string[]>(`${API_URL}/asignatura/${asignaturaId}/temas`, { 
    headers: getAuthHeader() 
  });
};

export const createPregunta = (pregunta: PreguntaDTO) => {
  return axios.post<PreguntaDTO>(API_URL, pregunta, {
    headers: getAuthHeader()
  });
};

export const updatePregunta = (id: number, pregunta: PreguntaDTO) => {
  return axios.put<PreguntaDTO>(`${API_URL}/${id}`, pregunta, {
    headers: getAuthHeader()
  });
};

export const deletePregunta = (id: number) => {
  return axios.delete(`${API_URL}/${id}`, {
    headers: getAuthHeader()
  });
};
