import axios from 'axios';
import { getCurrentUser } from './auth.service';

const API_URL = 'http://localhost:8080/api/config/';

const getAuthHeader = () => {
  const user = getCurrentUser();
  if (user && user.token) {
    return { Authorization: 'Bearer ' + user.token };
  }
  return {};
};

export const exportarConfiguracion = async () => {
  return axios.get(API_URL + 'exportar', {
    headers: getAuthHeader(),
    responseType: 'blob',
  });
};

export const importarConfiguracion = async (file: File) => {
  const formData = new FormData();
  formData.append('file', file);
  return await axios.post(API_URL + 'importar', formData, {
    headers: {
      ...getAuthHeader(),
      'Content-Type': 'multipart/form-data',
    },
  });
};
