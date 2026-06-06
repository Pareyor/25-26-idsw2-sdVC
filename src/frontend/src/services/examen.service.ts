import axios from 'axios';
import { authHeader } from './auth.service';

const API_URL = 'http://localhost:8080/api/examenes/';

const generarExamenes = (data: any) => {
  return axios.post(API_URL + 'generar', data, { headers: authHeader() });
};

const cancelarGeneracion = () => {
  return axios.delete(API_URL + 'generar/cancelar', { headers: authHeader() });
};

export default {
  generarExamenes,
  cancelarGeneracion,
};
