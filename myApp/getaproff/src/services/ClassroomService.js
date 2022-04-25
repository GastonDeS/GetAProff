import axios from "axios";

const PATH = '/classroom'


export class ClassroomService {

    async createPost(classId) {
        try {
            return await axios.post(`${PATH}/${classId}`)
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
}


