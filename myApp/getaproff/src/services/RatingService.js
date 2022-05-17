import {axiosService} from "./index";
import { paths, APPLICATION_V1_JSON_TYPE } from "../assets/constants";
import { handleResponse } from "../handlers/responseHandler";

const PATH = paths.RATINGS

export class RatingService {
  async getUserReviews(uid, page) {
    try {
        let config = {
            headers:  {'Content-Type' : APPLICATION_V1_JSON_TYPE}
        }
        config['params'] = {
            page: page,
            pageSize: 5
        }
        const res = await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/${uid}`, config);
        return handleResponse(res);
    } catch (error) {return handleResponse(error.response)}
  }

  //RatingController
  async rateTeacher(teacherId, data) {
    try {
        let form = {
            rate: parseFloat(data.rating),
            review: data.review
        };
        let config = {
          headers:  {'Content-Type' : APPLICATION_V1_JSON_TYPE}
        }
        const res = await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${teacherId}`, config, form);
        return handleResponse(res);
    } catch (error) {return handleResponse(error.response)}
}

}