import {axiosService} from "./index";
import { paths, APPLICATION_V1_JSON_TYPE } from "../assets/constants";

const PATH = paths.RATINGS

export class RatingService {
  async getUserReviews(uid, page) {
    try {
        let data;

        let config = {
            headers:  {'Content-Type' : APPLICATION_V1_JSON_TYPE}
        }
        config['params'] = {
            page: page,
            pageSize: 5
        }

        return await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/${uid}`, config)
    }
    catch (err) {
        console.log(err);
    }
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
        return await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${teacherId}`, config, form);
    }
    catch (err) {
        console.log(err);
    }
}

}