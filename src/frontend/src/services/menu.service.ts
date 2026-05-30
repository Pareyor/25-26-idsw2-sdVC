import axios from 'axios';
import { getCurrentUser } from './auth.service';

const API_URL = 'http://localhost:8080/api/menu';

export const getMenuOptions = async () => {
  const user = getCurrentUser();
  const token = user?.token;

  const response = await axios.get(API_URL, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
  return response.data;
};
