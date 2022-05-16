import {axiosService} from "./index";
import { paths, APPLICATION_V1_JSON_TYPE } from "../assets/constants";

const PATH = paths.RATINGS

export class RatingService {
  async getUserReviews(uid) {
    try {
        let data;
        let config = {}
        await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/${uid}`, config)
            .then(res => {
                data = res.data
            })
        return data;
    }
    catch (err) {
        console.log(err);
    }
}
}