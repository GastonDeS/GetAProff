import {axiosService} from "./index";
import { paths, APPLICATION_V1_JSON_TYPE } from "../assets/constants";

const PATH = paths.CLASSES

export class ClassesService {
    
    // Get user's classes filtered by params
    async getUserClasses(uid, asTeacher, status, page) {
        try {
            let config = {}
            config['params'] = {
                status: status,
                asTeacher: asTeacher,
                userId: uid,
                page: page,
                pageSize: 5
            }
            let response;
            await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}`, config)
                .then(r => response = r)
            return response
        }
        catch(err) {
            console.log(err);
        }
    }

    // Request a class from teacher
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