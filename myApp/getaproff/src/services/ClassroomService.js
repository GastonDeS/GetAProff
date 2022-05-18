import {axiosService} from "./index";
import { handleResponse } from "../handlers/responseHandler";

const PATH = 'classroom'
const APPLICATION_V1_JSON_TYPE = 'application/vnd.getaproff.api.v1+json'


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

    async acceptClass(classId, uid) {
        return await this.changeClassStatus(classId, 1, uid);
    }

    async finishClass(classId, uid){
        return await this.changeClassStatus(classId, 2, uid);
    }

    async cancelClass(classId, uid){
        return await this.changeClassStatus(classId, 4, uid);
    }

    async rateClass(classId, uid){
        return await this.changeClassStatus(classId, 3, uid);
    }

    async changeClassStatus(classId, newStatus, uid) {
        try {
            let postData = {
                status: newStatus,
                userId: uid
            }
            let config = {
                headers:  {'Content-Type' : APPLICATION_V1_JSON_TYPE}
            }
            const res = await axiosService.authAxiosWrapper(axiosService.POST,`${PATH}/${classId}/status`, config, postData);
            return handleResponse(res);
        } catch (error) {return handleResponse(error.response)}
    }

    async startSharingFile(fileIds, classroomId) {
        try {
            let config = {};
            let data = {ids: []};
            if (typeof fileIds === 'string')
                data.ids[0] = parseInt(fileIds)
            else {
                data.ids = fileIds.map(item => parseInt(item))
            }
            const res = await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${classroomId}/files`, config, data);
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


