import axios from "axios";

const getImage = (id, setImage) => {
  axios.get('/images/' + id)
    .then(res => {
      setImage('data:image/png;base64,' + res.data.image);
    })
    .catch(error => {});
};

export default {
  getImage,
};