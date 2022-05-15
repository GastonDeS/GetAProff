import {axiosService} from "./index";

const PATH = '/classes'
const APPLICATION_V1_JSON_TYPE = 'application/vnd.getaproff.api.v1+json'

export class ClassesService {
  async requestClass(requestData) {
    try {
        let config = {
          headers:  {'Content-Type' : APPLICATION_V1_JSON_TYPE}
        }
        let level = parseInt(requestData.level)
        let priceIdx = requestData.subject.levels.indexOf(level)
        let response;
        await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}`, config, {
            teacherId: requestData.teacherId,
            subjectId: requestData.subject.id,
            level: requestData.level,
            price: requestData.subject.prices[priceIdx],
        })
            .then(res => response = res);
        return response;
    }
    catch (err) {
        console.log(err)
    }
}
}