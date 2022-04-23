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

}