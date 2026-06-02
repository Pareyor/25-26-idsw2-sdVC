import axios from 'axios';

const API_URL = 'http://localhost:8080/api/alumnos';

export interface Alumno {
  id: number;
  niu: string;
  nombre: string;
  apellidos: string;
}

export const getAlumnos = () => {
  const userStr = localStorage.getItem('user');
  const token = userStr ? JSON.parse(userStr).token : null;
  return axios.get<Alumno[]>(API_URL, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
};
