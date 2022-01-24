import React from 'react'
import axios from "axios";

const API_URL = "api/auth/";

const login = async (formData) => {
  await axios({
      method: "post",
      url: API_URL + "login",
      data: formData,
      headers: { "Content-Type": "multipart/form-data" },
    })
    .then(response => {
      if (response.data.token) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }
      console.log(response.data);
      return response.data;
    })
    // .catch(response => {
    //   console.log(response);
    //   return response.message;
    // });
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

export default {
  login,
  getCurrentUser,
};