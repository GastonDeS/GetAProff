import {axiosService} from "./index";
import { handleResponse } from "../handlers/responseHandler";

const PATH = paths.CLASSROOM

export class ClassroomService {

    async createPost(classId, data, uid) {
        try {
            let formData = new FormData();
            formData.append("message", data.postTextInput);
            formData.append("file", data.file[0]);
            formData.append("uploader", uid);
            let config = {
                headers:  {'Content-Type' : 'multipart/form-data'}
            }
            const res = await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${classId}/posts`, config, formData);
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    async fetchClassroomInfo(classId) {
        try {
            const res = await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/${classId}`, {});
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    async fetchClassroomPosts(classId, page) {
        try {
            let config = {params: {page: page, pageSize: 5}}
            const res = await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/${classId}/posts`, config);
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    async acceptClass(classId) {
        return await this.changeClassStatus(classId, classStatus.ACCEPTED);
    }

    async finishClass(classId){
        return await this.changeClassStatus(classId, classStatus.FINISHED);
    }

    async cancelClassS(classId){
        return await this.changeClassStatus(classId, classStatus.CANCELLEDS);
    }

    async cancelClassT(classId){
        return await this.changeClassStatus(classId, classStatus.CANCELLEDT);
    }

    async rejectClass(classId){
        return await this.changeClassStatus(classId, classStatus.REJECTED);
    }

    async rateClass(classId){
        return await this.changeClassStatus(classId, classStatus.RATED);
    }

    async changeClassStatus(classId, newStatus) {
        try {
            const res = await axiosService.authAxiosWrapper(axiosService.POST,`${PATH}/${classId}/${newStatus}`, {});
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    async startSharingFile(fileIds, classroomId) {
        try {
                let config = {};
                let data = [];
                if (typeof fileIds === 'string')
                    data[0] = parseInt(fileIds)
                else {
                    data = fileIds.map(item  => parseInt(item))
                }
                const res = await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${classroomId}/files`,config, data);
                return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    async stopSharingFile(fileId, classroomId){
        try {
            let config = {};
            const res = await axiosService.authAxiosWrapper(axiosService.DELETE, `${PATH}/${classroomId}/files/${fileId}`,config);
                return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    async getClassroomFiles(classroomId) {
        try {
                const res = await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/${classroomId}/files`, {})
                return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

}


