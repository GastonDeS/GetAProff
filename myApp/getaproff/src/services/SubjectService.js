import {axiosService} from "./index";
import { handleResponse } from "../handlers/responseHandler";

const PATH = '/subjects'

export class SubjectService {
  async getMostRequestedSubjects() {
    try {
      const res = await axiosService.axiosWrapper(axiosService.GET, `${PATH}/most-requested`, {});
      return handleResponse(res);
    } catch (error) {return handleResponse(error.response)}
  }
}
