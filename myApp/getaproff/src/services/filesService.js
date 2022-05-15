import { axiosService } from "./index";

const USER_FILE_PATH = 'user-files'
const SUBJECT_FILE_PATH = 'subject-files'

export class FilesService {
  // async getUserFile (id, setLink) {
  //   axios.get('/files/user/' + id)
  //     .then(res => {
  //       setLink(window.URL.createObjectURL(new Blob([res.data])));
  //     })
  //     .catch(error => {});
  // };

  base64ToArrayBuffer (base64) {
    var binaryString = window.atob(base64);
    var len = binaryString.length;
    var bytes = new Uint8Array(len);
    for (var i = 0; i < len; i++) {
        bytes[i] = binaryString.charCodeAt(i);
    }
    return bytes.buffer;
  }

  async getUserCertifications (uid) {
    try {
      let data;
      let config = {}
      await axiosService.authAxiosWrapper(axiosService.GET, `/${USER_FILE_PATH}/${uid}`, config)
          .then(res => {
              data = res.data
          })
      return data;
    }
    catch (err) {
        console.log(err);
    }
  }

  async addCertification (uid, form) {
    try {
       await axiosService.authAxiosWrapper(axiosService.POST, `/${USER_FILE_PATH}/${uid}`, {}, form);
       return;
    } catch (err) {
        console.log(err);
    }
  }

  async removeCertification (fileId) {
    try {
        await axiosService.authAxiosWrapper(axiosService.DELETE, `/${USER_FILE_PATH}/${fileId}`, {});
        return;
    } catch (err) {
        console.log(err);
    }
  }

  async getSubjectFiles (uid) {
    try {
      const res = await axiosService.authAxiosWrapper(axiosService.GET, `/${SUBJECT_FILE_PATH}/${uid}`, {});
      return res.data;
    } catch (err) {
        console.log(err);
    }
  }

  async addSubjectFiles (uid, level, subject, form) {
    try {
      await axiosService.authAxiosWrapper(axiosService.POST, `/${SUBJECT_FILE_PATH}/${uid}/${subject}/${level}`, {}, form);
    } catch (err) {
      console.log(err);
    }
  }

  async removeSubjectFiles (fileId) {
    try {
      await axiosService.authAxiosWrapper(axiosService.DELETE, `/${SUBJECT_FILE_PATH}/${fileId}`, {});
    } catch (err) {
      console.log(err);
    }
  }
}