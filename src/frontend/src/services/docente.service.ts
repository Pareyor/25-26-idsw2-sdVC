import axios from 'axios';

const API_URL = 'http://localhost:8080/api/docentes';

export interface Docente {
  id: number;
  username: string;
  email: string;
  nombre: string;
  apellidos: string;
}

const getDocentes = () => {
  const token = localStorage.getItem('token');
  return axios.get<Docente[]>(API_URL, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
};

const DocenteService = {
  getDocentes
};

export default DocenteService;
