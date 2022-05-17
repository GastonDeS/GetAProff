import {axiosService} from "./index";
import { handleResponse } from "../handlers/responseHandler";
import { handleService } from "../handlers/serviceHandler";
import {paths, APPLICATION_V1_JSON_TYPE} from "../assets/constants";

const PATH = paths.SUBJECTS

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

  async requestSubject(requestData) {
    try {
      let config = {
        headers:  {'Content-Type' : APPLICATION_V1_JSON_TYPE}
      }
      let subject = requestData.subject
      let message = requestData.text
      let response;
      await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}`, config, {
        subject: subject,
        message: message,
      }).then(res => response = res);
      return response;
    }
    catch (err) {
      console.log(err)
    }
  }
}
