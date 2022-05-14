import { UserService} from "./userService";
import { ClassroomService } from "./classroomService";
import { AxiosService } from "./axios.service";


const userService = new UserService();
const classroomService = new ClassroomService();
const axiosService = new AxiosService();


export {
    userService,
    classroomService,
    axiosService
};
