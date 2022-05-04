import axios from "axios";

const PATH = '/classroom'


export class ClassroomService {

    async createPost(classId, data, uid) {
        try {
            let response;
            let formData = new FormData();
            formData.append("message", data.postTextInput);
            formData.append("file", data.file[0]);
            formData.append("uploader", uid);
            await axios.post(`${PATH}/${classId}/posts`, formData, {
                headers: { 'Content-Type' : 'multipart/form-data' }
            }).then(res => response = res)
            return response;
        }
        catch (err) {
            console.log(err);
        }
    }

    async fetchClassroomInfo(classId) {
        try {
            let data;
            await axios.get(`${PATH}/${classId}`)
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
            await axios.get(`${PATH}/${classId}/posts`, {params: {page: page, pageSize: 5}}).then(
                res => {
                    response['data'] = res.data;
                    response['pageQty'] =(parseInt(res.headers['x-total-pages']) + 1)
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
            let data;
            await axios.post(`${PATH}/${classId}/status`, postData)
                .then(res => data = res.data
                )
            return data;
        }
        catch (err) {
            console.log(err)
        }
    }
}


