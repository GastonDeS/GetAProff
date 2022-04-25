import axios from "axios";

const PATH = '/classroom'


export class ClassroomService {

    async createPost(classId, data, uid) {
        try {
            let response;
            let formData = new FormData();
            formData.append("message", data.postTextInput);
            formData.append("file", data.file);
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
    async fetchClassroomPosts(classId) {
        try {
            let data;
            await axios.get(`${PATH}/${classId}/posts`).then(
                res => data = res.data
            )
            return data;
        }
        catch (err) {
            console.log(err);
        }
    }
}


