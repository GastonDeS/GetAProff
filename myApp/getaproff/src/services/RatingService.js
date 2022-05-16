import {axiosService} from "./index";

const PATH = '/ratings'
const APPLICATION_V1_JSON_TYPE = 'application/vnd.getaproff.api.v1+json'

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