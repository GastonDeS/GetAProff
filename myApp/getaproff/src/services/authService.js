import {axiosService} from "./index";

const API_URL = "user";

const login = async (mail, password) => {
  try {
    let config = {}
    config['headers'] =  {Authorization: axiosService.getBasicToken(mail, password)}
    const response = await axiosService.axiosWrapper(axiosService.GET, `${API_URL}`, config);
    const token = response.headers.authorization.split(" ")[1];

    if (token) localStorage.setItem('token', token);
    if (response.data) localStorage.setItem('user', JSON.stringify(response.data));

    return token;
  } catch (err) {
    console.log(err);
  }
};
const toTeacher = (user) => {
  localStorage.setItem('user', JSON.stringify(user));
}

const logout = () => {
  localStorage.removeItem('user');
  localStorage.removeItem('token');
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem('user'));
};

export default {
  login,
  getCurrentUser,
  logout,
  toTeacher
};