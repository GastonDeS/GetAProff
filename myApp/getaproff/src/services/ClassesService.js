import {axiosService} from "./index";
import { paths } from "../assets/constants";
import { handleResponse } from "../handlers/responseHandler";

const PATH = paths.CLASSES

export class ClassesService {
    
    async getUserClasses(asTeacher, status, page) {
        try {
            let config = {}
            config['params'] = {
                asTeacher: asTeacher,
                status: status,
                page: page,
                pageSize: 5
            }
            const res = await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}`, config);
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    async requestClass(requestData) {
        try {
            let level = parseInt(requestData.level)
            let priceIdx = requestData.subject.levels.indexOf(level)
            const res = await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}`, {}, {
                teacherId: requestData.teacherId,
                subjectId: requestData.subject.id,
                level: requestData.level,
                price: requestData.subject.prices[priceIdx],
            })
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }
}