import {axiosService} from "./index";
import { handleResponse } from "../handlers/responseHandler";
import { handleService } from "../handlers/serviceHandler";

const PATH = '/subjects'

export class SubjectService {
  async getMostRequestedSubjects(navigate, setSubjects) {
      await axiosService.axiosWrapper(axiosService.GET, `${PATH}/most-requested`, {})
        .then((response) => {
          let ans = handleResponse(response);
          setSubjects(handleService(ans, navigate));
        }).catch((error) => {
          let ans = handleResponse(error.response);
          handleService(ans, navigate);
        }).finally(() => {return});
  }
}
