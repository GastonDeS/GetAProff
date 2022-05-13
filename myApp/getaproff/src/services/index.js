import { UserService} from "./UserService";
import { ClassroomService } from "./ClassroomService";
import {AxiosService} from "./axios.service";


const userService = new UserService();
const classroomService = new ClassroomService();
const axiosService = new AxiosService();


export {
    userService,
    classroomService,
    axiosService
};
