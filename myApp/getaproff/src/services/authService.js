import axios from "axios";

const API_URL = "auth/";

const login = async (mail, password) => {
  await axios.post(API_URL + 'login', {
      mail,
      password,
    })
    .then(response => {
      if (response.data.token) {
        localStorage.setItem('user', JSON.stringify(response.data));
      };
      return response.data;
    });
};

const logout = () => {
  localStorage.removeItem('user');
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem('user'));
};

export default {
  login,
  getCurrentUser,
  logout,
};