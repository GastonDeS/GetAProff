import axios from "axios";

const API_URL = "/users";

const login = async (mail, password) => {
    //TODO: corregir post sin /login
  await axios.post(API_URL  + "/login", {
      mail,
      password,
    })
    .then(response => {
      if (response.data.token) {
        localStorage.setItem('user', JSON.stringify(response.data));
      }
      return response.data;
    });
};


const register = async (name, mail, password, description, schedule) => {
  return await axios
      .post(API_URL, {
        name,
        mail,
        password,
        description,
        schedule,
      })
      .then( response => {return response;});
}
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
  register
};