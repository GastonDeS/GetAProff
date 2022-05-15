import { UserService} from "./UserService";
import { ClassroomService } from "./ClassroomService";
import { AxiosService } from "./axios.service";
import { FilesService } from "./FilesService";


const userService = new UserService();
const classroomService = new ClassroomService();
const axiosService = new AxiosService();
const filesService = new FilesService();


export {
    userService,
    classroomService,
    axiosService,
    filesService
};
