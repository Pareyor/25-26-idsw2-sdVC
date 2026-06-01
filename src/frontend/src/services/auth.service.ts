import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth/';

export const login = async (username: string, password: string) => {
  const response = await axios.post(API_URL + 'login', {
    username,
    password,
  });
  if (response.data.token) {
    localStorage.setItem('user', JSON.stringify(response.data));
  }
  return response.data;
};

export const logout = async () => {
  const user = getCurrentUser();
  if (user?.token) {
    try {
      await axios.post(API_URL + 'logout', {}, {
        headers: { Authorization: `Bearer ${user.token}` }
      });
    } catch (err) {
      console.error("Error al notificar logout al servidor", err);
    }
  }
  localStorage.removeItem('user');
};

export const getCurrentUser = () => {
  const userStr = localStorage.getItem('user');
  if (userStr) {
    try {
      return JSON.parse(userStr);
    } catch (e) {
      localStorage.removeItem('user');
      return null;
    }
  }
  return null;
};
