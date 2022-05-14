import {axiosService} from "./index";

const API_URL = "/auth";
const APPLICATION_V1_JSON_TYPE = 'application/vnd.getaproff.api.v1+json'

const login = async (mail, password) => {
  const credentials = mail+":"+password;
  const hash = btoa(credentials);
  try {
    let config = {}
    config['headers'] =  {Authorization: axiosService.getBasicToken(mail, password)}
    const response = await axiosService.axiosWrapper(axiosService.GET, `${API_URL}/login`, config);
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

const register = async (formData) => {
  try {
    let config = {
      headers:  {'Content-Type' : APPLICATION_V1_JSON_TYPE}
    }
    var url = API_URL + '/student';
    if (formData.role === 1) {
      url = API_URL + '/teacher';
    }
    await axiosService.axiosWrapper(axiosService.POST, url, config, formData)
    .then( (res) => {
      localStorage.setItem('token', res.headers.authorization);
      localStorage.setItem('user', JSON.stringify(res.data));
    });
  } catch (err) {
    console.log(err);
  }
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
  register,
  toTeacher
};