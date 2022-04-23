import { UserService} from "./UserService";
import { ClassroomService } from "./ClassroomService";


const userService = new UserService();
const classroomService = new ClassroomService();


export {
    userService,
    classroomService
};
