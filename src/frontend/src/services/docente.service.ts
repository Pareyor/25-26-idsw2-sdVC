import axios from 'axios';

const API_URL = 'http://localhost:8080/api/docentes';

export interface Docente {
  id: number;
  username: string;
  email: string;
  nombre: string;
  apellidos: string;
}

const getAuthHeader = () => {
  const userStr = localStorage.getItem('user');
  const token = userStr ? JSON.parse(userStr).token : null;
  return { Authorization: `Bearer ${token}` };
};

export const getDocentes = () => {
  return axios.get<Docente[]>(API_URL, {
    headers: getAuthHeader()
  });
};

export const createDocente = (docente: Omit<Docente, 'id'>) => {
  return axios.post<Docente>(API_URL, docente, {
    headers: getAuthHeader()
  });
};

export const getDocente = (id: number) => {
  return axios.get<Docente>(`${API_URL}/${id}`, {
    headers: getAuthHeader()
  });
};

export const updateDocente = (id: number, docente: Docente) => {
  return axios.put<Docente>(`${API_URL}/${id}`, docente, {
    headers: getAuthHeader()
  });
};

export const deleteDocente = (id: number) => {
  return axios.delete(`${API_URL}/${id}`, {
    headers: getAuthHeader()
  });
};
