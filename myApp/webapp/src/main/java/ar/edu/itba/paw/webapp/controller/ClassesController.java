package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.LectureService;
import ar.edu.itba.paw.interfaces.services.UserFileService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.ClassroomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/classes")
@Controller
public class ClassesController {

    @Autowired
    LectureService lectureService;

    @Autowired
    UserService userService;

    @Context
    UriInfo uriInfo;

    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response getClassesFromUser(@QueryParam("studentId") @DefaultValue("0") Long studentId,
                                       @QueryParam("teacherId") @DefaultValue("0") Long teacherId,
                                       @QueryParam("status") @DefaultValue("-1") int status) {
        List<Lecture> lectures = new ArrayList<>();
        //TODO: chequear esto cuadno ande la auth
//        Optional<User> mayBeUser = userService.getCurrentUser();
//        mayBeUser.isPresent() && Objects.equals(mayBeUser.get().getId(), studentId)
        if(studentId != 0 )
            lectures = lectureService.findClassesByStudentAndStatus(studentId, status);
        else if(teacherId !=0)
            lectures = lectureService.findClassesByTeacherAndStatus(teacherId, status);
        if (lectures.isEmpty())
            return Response.status(Response.Status.NO_CONTENT).build();
        List<ClassroomDto> dtos = lectures.stream().map(lecture -> ClassroomDto.getClassroom(uriInfo,lecture)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<ClassroomDto>>(dtos){}).build();
    }
}
