import axios from 'axios';
import { authHeader } from './auth.service';

const API_URL = 'http://localhost:8080/api/examenes/';

const generarExamenes = (data: any) => {
  return axios.post(API_URL + 'generar', data, { headers: authHeader() });
};

const getBorradores = () => {
  return axios.get(API_URL + 'generar/borradores', { headers: authHeader() });
};

const asignarExamenes = (alumnoIds: number[]) => {
  return axios.post(API_URL + 'asignar', { alumnoIds }, { headers: authHeader() });
};

const cancelarGeneracion = () => {
  return axios.delete(API_URL + 'generar/cancelar', { headers: authHeader() });
};

const getExamenesParaCorregir = () => {
  return axios.get(API_URL + 'corregir/listar', { headers: authHeader() });
};

const corregirExamen = (examenId: number) => {
  return axios.post(API_URL + 'corregir/' + examenId, {}, { headers: authHeader() });
};

export default {
  generarExamenes,
  getBorradores,
  asignarExamenes,
  cancelarGeneracion,
  getExamenesParaCorregir,
  corregirExamen,
};
