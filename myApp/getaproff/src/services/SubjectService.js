import {axiosService} from "./index";
import { handleResponse } from "../handlers/responseHandler";
import { paths, APPLICATION_V1_JSON_TYPE } from "../assets/constants";

const PATH = paths.SUBJECTS

export class SubjectService {
  async getMostRequestedSubjects() {
    try {
      const res = await axiosService.axiosWrapper(axiosService.GET, `${PATH}/most-requested`, {});
      return handleResponse(res);
    } catch (error) {return handleResponse(error.response)}
  }

  async requestSubject(requestData) {
    try {
      let config = {
        headers:  {'Content-Type' : APPLICATION_V1_JSON_TYPE}
      }
      const res = await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}`, config, requestData);
      return handleResponse(res);
    } catch (error) {return handleResponse(error.response)}
  }
}
