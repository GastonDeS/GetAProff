package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.LectureService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.webapp.dto.ClassroomDto;
import ar.edu.itba.paw.webapp.exceptions.ClassNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/classroom")
@Component
public class ClassroomController {
    @Autowired
    private UserService userService;

    @Autowired
    private LectureService lectureService;

//    @GET
//    @Path("/")
//    public Response getClassroom() {
//        lectureService.findClassesByStudentAndStatus()
//        return Response.ok().build();
//    }

    @GET
    @Path("/{classId}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response getClassroom(@PathParam("classId") final Long classId) {
        Lecture lecture = checkLectureExistence(classId);
        return Response.ok(
                ClassroomDto.getClassroom(lecture)
        ).build();
    }

    private Lecture checkLectureExistence(Long classId) throws ClassNotFoundException {
        Optional<Lecture> maybeClass = lectureService.findById(classId);
        if (!maybeClass.isPresent()) {
            throw new ClassNotFoundException("No class found for class id " + classId);
        }
        return maybeClass.get();
    }
}
