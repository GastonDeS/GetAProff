import {axiosService} from "./index";

const API_URL = "/auth";

const login = async (mail, password) => {
  const credentials = mail+":"+password;
  const hash = btoa(credentials);
  try {
    // const response = await axios.get(API_URL+"/login", {headers: { Authorization: "Basic "+hash }})
    // const path = 
    // const headers = {Authorization: getBasicToken()}
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

// const login = async (mail, password) => {
//     //TODO: corregir post sin /login
//   await axios.post(API_URL  + "/login", {
//       mail,
//       password,
//     })
//     .then(response => {
//       if (response.data.token) {
//         localStorage.setItem('user', JSON.stringify(response.data));
//       }
//       return response.data;
//     });
// };

const toTeacher = (user) => {
  localStorage.setItem('user', JSON.stringify(user));
}

const register = async (formData) => {
  return await axiosService.axiosWrapper(axiosService.POST, API_URL, {'Content-Type': 'multipart/form-data'}, formData);
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