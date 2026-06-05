import axios from 'axios';

const API_URL = 'http://localhost:8080/api/alumnos';

export interface Alumno {
  id: number;
  dni: string;
  nombre: string;
  apellidos: string;
}

const getAuthHeader = () => {
  const userStr = localStorage.getItem('user');
  const token = userStr ? JSON.parse(userStr).token : null;
  return { Authorization: `Bearer ${token}` };
};

export const getAlumnos = () => {
  return axios.get<Alumno[]>(API_URL, {
    headers: getAuthHeader()
  });
};

export const getAlumno = (id: number) => {
  return axios.get<Alumno & { gradoId: number }>(`${API_URL}/${id}`, {
    headers: getAuthHeader()
  });
};

export const createAlumno = (alumno: Omit<Alumno, 'id'> & { gradoId: number }) => {
  return axios.post<Alumno>(API_URL, alumno, {
    headers: getAuthHeader()
  });
};

export const updateAlumno = (id: number, alumno: Alumno & { gradoId: number }) => {
  return axios.put<Alumno>(`${API_URL}/${id}`, alumno, {
    headers: getAuthHeader()
  });
};
