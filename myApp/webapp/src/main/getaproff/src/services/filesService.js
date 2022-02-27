import axios from "axios"

const getUserFile = (id, setLink) => {
  axios.get('/files/user/' + id)
    .then(res => {
      setLink(window.URL.createObjectURL(new Blob([res.data])));
    })
    .catch(error => {});
};

const base64ToArrayBuffer = (base64) => {
  var binaryString = window.atob(base64);
  var len = binaryString.length;
  var bytes = new Uint8Array(len);
  for (var i = 0; i < len; i++) {
      bytes[i] = binaryString.charCodeAt(i);
  }
  return bytes.buffer;
}

export default {
  getUserFile,
  base64ToArrayBuffer,
}