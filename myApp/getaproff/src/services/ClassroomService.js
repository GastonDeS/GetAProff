import {axiosService} from "./index";

const PATH = '/classroom'
const APPLICATION_V1_JSON_TYPE = 'application/vnd.getaproff.api.v1+json'


export class ClassroomService {

    async createPost(classId, data, uid) {
        try {
            let response;
            let formData = new FormData();
            formData.append("message", data.postTextInput);
            formData.append("file", data.file[0]);
            formData.append("uploader", uid);
            let config = {
                headers:  {'Content-Type' : 'multipart/form-data'}
            }
            await axiosService.authAxiosWrapper(axiosService.POST, `${PATH}/${classId}/posts`, config, formData )
                .then(res => response = res)
            return response;
        }
        catch (err) {
            console.log(err);
        }
    }

    async fetchClassroomInfo(classId) {
        try {
            let data;
            let config = {}
            await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/${classId}`, config)
                .then(res => data = res.data);
            return data;
        }
        catch (err) {
            console.log(err);
        }
    }
    async fetchClassroomPosts(classId, page) {
        try {
            let response = {}
            let config = {params: {page: page, pageSize: 5}}
            await axiosService.authAxiosWrapper(axiosService.GET, `${PATH}/${classId}/posts`, config)
                .then(
                    res => {
                        response['data'] = res.data;
                        response['pageQty'] =(parseInt(res.headers['x-total-pages']))
                    }
                )
            return response;
        }
        catch (err) {
            console.log(err);
        }
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
            return await axiosService.authAxiosWrapper(axiosService.POST,`${PATH}/${classId}/status`, config, postData);
        }
        catch (err) {
            console.log(err)
        }
    }
}


