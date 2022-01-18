import React, {useEffect} from 'react'
import axios from "axios";

const API_URL = "api/auth/";

const login = async (mail) => {
  await axios.post(API_URL + 'login', {
    params: {mail: mail}
  })
    .then(response => {
      if (response.data.token) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }
      return response.data;
    });
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

export default {
  login,
  getCurrentUser,
};