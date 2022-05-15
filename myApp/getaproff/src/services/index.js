import { UserService} from "./UserService";
import { ClassroomService } from "./ClassroomService";
import { AxiosService } from "./axios.service";
import { FilesService } from "./filesService";
import { SubjectService } from "./SubjectService";


const userService = new UserService();
const classroomService = new ClassroomService();
const axiosService = new AxiosService();
const filesService = new FilesService();
const subjectService = new SubjectService();

export {
    userService,
    classroomService,
    axiosService,
    filesService,
    subjectService
};
