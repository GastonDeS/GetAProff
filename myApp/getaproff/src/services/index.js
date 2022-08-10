import { UserService} from "./UserService";
import { ClassroomService } from "./ClassroomService";
import { AxiosService } from "./axios.service";
import { FilesService } from "./FilesService";
import { SubjectService } from "./SubjectService";
import { ClassesService } from "./ClassesService";
import { RatingService } from "./RatingService";
import { FavouritesService } from "./FavouritesService";


const userService = new UserService();
const classroomService = new ClassroomService();
const axiosService = new AxiosService();
const filesService = new FilesService();
const subjectService = new SubjectService();
const classesService = new ClassesService();
const ratingService = new RatingService();
const favouritesService = new FavouritesService();

export {
    userService,
    classroomService,
    axiosService,
    filesService,
    subjectService,
    classesService,
    ratingService,
    favouritesService
};
