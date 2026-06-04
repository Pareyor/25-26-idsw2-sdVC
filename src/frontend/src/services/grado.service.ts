import axios from 'axios';

const API_URL = 'http://localhost:8080/api/grados';

export interface Grado {
  id: number;
  codigo: string;
  titulo: string;
}

const getAuthHeader = () => {
  const userStr = localStorage.getItem('user');
  const token = userStr ? JSON.parse(userStr).token : null;
  return { Authorization: `Bearer ${token}` };
};

export const getGrados = () => {
  return axios.get<Grado[]>(API_URL, {
    headers: getAuthHeader()
  });
};

export const getGrado = (id: number) => {
  return axios.get<Grado>(`${API_URL}/${id}`, {
    headers: getAuthHeader()
  });
};

export const createGrado = (grado: Omit<Grado, 'id'>) => {
  return axios.post<Grado>(API_URL, grado, {
    headers: getAuthHeader()
  });
};

export const updateGrado = (id: number, grado: Grado) => {
  return axios.put<Grado>(`${API_URL}/${id}`, grado, {
    headers: getAuthHeader()
  });
};
