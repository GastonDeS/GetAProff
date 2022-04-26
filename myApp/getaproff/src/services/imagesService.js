import axios from "axios";

const getImage = (id, setImage) => {
  axios.get(`/users/${id}/image`)
    .then(res => {
      setImage('data:image/png;base64,' + res.data.image);
    })
    .catch(error => {});
};

export default {
  getImage,
};