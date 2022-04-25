package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.LectureService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.webapp.dto.ClassroomDto;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
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
    public Response getClassesFromUser(@QueryParam("userId") @DefaultValue("0") Long userId,
                                       @QueryParam("asTeacher") @DefaultValue("false") Boolean asTeacher,
                                       @QueryParam("status") @DefaultValue("-1") int status,
                                       @QueryParam("page") @DefaultValue("1") Integer page,
                                       @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        try {
            final Page<Lecture> lectures = lectureService.findClasses(userId, asTeacher, status, page, pageSize);
            Response.ResponseBuilder builder = Response.ok(
                    new GenericEntity<List<ClassroomDto>>(lectures.getContent().stream()
                            .map(lecture -> ClassroomDto.getClassroom(uriInfo, lecture))
                            .collect(Collectors.toList())) {
                    });
            return PaginationBuilder.build(lectures, builder, uriInfo, pageSize);
        } catch (IllegalArgumentException exception) { //TODO mensaje exacto
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
