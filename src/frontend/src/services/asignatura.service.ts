import axios from 'axios';

const API_URL = 'http://localhost:8080/api/asignaturas';

export interface Asignatura {
  id: number;
  codigo: string;
  titulo: string;
  cursoAcademico: string;
}

export const getAsignaturas = () => {
  const userStr = localStorage.getItem('user');
  const token = userStr ? JSON.parse(userStr).token : null;
  return axios.get<Asignatura[]>(API_URL, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
};
