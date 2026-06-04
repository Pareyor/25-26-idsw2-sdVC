import axios from 'axios';

const API_URL = 'http://localhost:8080/api/asignaturas';

export interface Asignatura {
  id: number;
  codigo: string;
  titulo: string;
  cursoAcademico: string;
}

const getAuthHeader = () => {
  const userStr = localStorage.getItem('user');
  const token = userStr ? JSON.parse(userStr).token : null;
  return { Authorization: `Bearer ${token}` };
};

export const getAsignaturas = () => {
  return axios.get<Asignatura[]>(API_URL, {
    headers: getAuthHeader()
  });
};

export const getAsignatura = (id: number) => {
  return axios.get<Asignatura & { gradoId: number }>(`${API_URL}/${id}`, {
    headers: getAuthHeader()
  });
};

export const createAsignatura = (asignatura: Omit<Asignatura, 'id'> & { gradoId: number }) => {
  return axios.post<Asignatura>(API_URL, asignatura, {
    headers: getAuthHeader()
  });
};

export const updateAsignatura = (id: number, asignatura: Asignatura & { gradoId: number }) => {
  return axios.put<Asignatura>(`${API_URL}/${id}`, asignatura, {
    headers: getAuthHeader()
  });
};

export const deleteAsignatura = (id: number) => {
  return axios.delete(`${API_URL}/${id}`, {
    headers: getAuthHeader()
  });
};
