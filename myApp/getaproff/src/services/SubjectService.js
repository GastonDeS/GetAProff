import {axiosService} from "./index";

const PATH = '/subjects'

export class SubjectService {
  async getMostRequestedSubjects() {
    try {
      return await axiosService.axiosWrapper(axiosService.GET, `${PATH}/most-requested`, {});
    } catch (err) {
      console.log(err)
    }
  }
}
