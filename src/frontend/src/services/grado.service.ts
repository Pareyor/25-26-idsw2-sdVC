import axios from 'axios';

const API_URL = 'http://localhost:8080/api/grados';

export interface Grado {
  id: number;
  codigo: string;
  titulo: string;
}

export const getGrados = () => {
  const userStr = localStorage.getItem('user');
  const token = userStr ? JSON.parse(userStr).token : null;
  return axios.get<Grado[]>(API_URL, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
};
