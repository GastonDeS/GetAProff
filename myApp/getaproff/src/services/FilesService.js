import { axiosService } from "./index";
import { paths } from "../assets/constants";
import { handleResponse } from "../handlers/responseHandler";

const USER_FILE_PATH = paths.USER_FILES
const SUBJECT_FILE_PATH = paths.SUBJECT_FILES
const POST_PATH = paths.POST

export class FilesService {

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
      let config = {};
      config['params'] = {
        id: Number(uid)
      };
      const res = await axiosService.authAxiosWrapper(axiosService.GET, `${USER_FILE_PATH}`, config);
      return handleResponse(res);
    } catch (error) {return handleResponse(error.response)}
  }

  async addCertification (uid, form) {
    try {
       const res = await axiosService.authAxiosWrapper(axiosService.POST, `${USER_FILE_PATH}/${uid}`, {}, form);
       return handleResponse(res);
    } catch (error) {return handleResponse(error.response)}
  }

  async removeCertification (fileId) {
    try {
        const res = await axiosService.authAxiosWrapper(axiosService.DELETE, `/${USER_FILE_PATH}/${fileId}`, {});
        return handleResponse(res);
    } catch (error) {return handleResponse(error.response)}
  }

  async getCertificationById (fileId) {
    try {
      const res = await axiosService.authAxiosWrapper(axiosService.GET, `${USER_FILE_PATH}/${fileId}`, {});
      return handleResponse(res);
    } catch (error) {return handleResponse(error.response)}
  }

  async getSubjectFiles () {
    try {
      const res = await axiosService.authAxiosWrapper(axiosService.GET, `${SUBJECT_FILE_PATH}`, {});
      return handleResponse(res);
    } catch (error) {return handleResponse(error.response)}
  }

  async getSubjectFile(fileId) {
    try {
      const res = await axiosService.authAxiosWrapper(axiosService.GET, `${SUBJECT_FILE_PATH}/${parseInt(fileId)}`, {});
      return handleResponse(res);
    } catch (error) {return handleResponse(error.response)}
  }

  async getPostFile (postId) {
    try {
      const res = await axiosService.authAxiosWrapper(axiosService.GET, `${POST_PATH}/${parseInt(postId)}/file`, {});
      return handleResponse(res);
    } catch (error) {return handleResponse(error.response)}
  }

  async addSubjectFiles (form) {
    try {
      const res = await axiosService.authAxiosWrapper(axiosService.POST, `${SUBJECT_FILE_PATH}`, {}, form);
      return handleResponse(res);
    } catch (error) {return handleResponse(error.response)}
  }

  async removeSubjectFiles (fileId) {
    try {
      const res = await axiosService.authAxiosWrapper(axiosService.DELETE, `${SUBJECT_FILE_PATH}/${fileId}`, {});
      return handleResponse(res);
    } catch (error) {return handleResponse(error.response)}
  }
}