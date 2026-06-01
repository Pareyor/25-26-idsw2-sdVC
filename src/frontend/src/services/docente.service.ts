import axios from 'axios';

const API_URL = 'http://localhost:8080/api/docentes';

export interface Docente {
  id: number;
  username: string;
  email: string;
  nombre: string;
  apellidos: string;
}

export const getDocentes = () => {
  const userStr = localStorage.getItem('user');
  const token = userStr ? JSON.parse(userStr).token : null;
  return axios.get<Docente[]>(API_URL, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
};
