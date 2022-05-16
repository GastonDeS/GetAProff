package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.LectureService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.webapp.dto.ClassroomDto;
import ar.edu.itba.paw.webapp.requestDto.ClassRequestDto;
import ar.edu.itba.paw.webapp.security.services.AuthFacade;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api/classes")
@Controller
public class ClassesController {

    @Autowired
    LectureService lectureService;

    @Autowired
    UserService userService;

    @Context
    UriInfo uriInfo;

    @Autowired
    private AuthFacade authFacade;

    @Autowired
    EmailService emailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassesController.class);

    @GET
    @Produces("application/vnd.getaproff.api.v1+json")
    @Consumes("application/vnd.getaproff.api.v1+json")
    public Response getClassesFromUser(
                                       @QueryParam("asTeacher") @DefaultValue("false") Boolean asTeacher,
                                       @QueryParam("status") @DefaultValue("-1") int status,
                                       @QueryParam("page") @DefaultValue("1") Integer page,
                                       @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        try {
            Long userId = authFacade.getCurrentUserId();
            final Page<Lecture> lectures = lectureService.findClasses(userId, asTeacher, status, page, pageSize);
            Response.ResponseBuilder builder = Response.ok(
                    new GenericEntity<List<ClassroomDto>>(lectures.getContent().stream()
                            .map(ClassroomDto::getClassroom)
                            .collect(Collectors.toList())) {
                    });
            return PaginationBuilder.build(lectures, builder, uriInfo, pageSize);
        } catch (IllegalArgumentException exception) { //TODO mensaje exacto
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Consumes(value = "application/vnd.getaproff.api.v1+json")
    public Response requestClass(ClassRequestDto classRequestDto) {
        Optional<Lecture> newLecture = lectureService.create(authFacade.getCurrentUserId(), classRequestDto.getTeacherId(), classRequestDto.getLevel(),
                classRequestDto.getSubjectId(), classRequestDto.getPrice());
        if(!newLecture.isPresent()){
            return Response.status(Response.Status.CONFLICT).build();
        }
        LOGGER.debug("requested class of subject with id {} from teacher with id{}, level: {}, price: {}", classRequestDto.getSubjectId(),classRequestDto.getTeacherId(), classRequestDto.getLevel(), classRequestDto.getPrice());
        emailService.sendNewClassMessage(newLecture.get().getTeacher().getMail(), newLecture.get().getStudent().getName(), newLecture.get().getSubject().getName(), newLecture.get().getClassId(), uriInfo.getBaseUri().toString());
        URI location = URI.create(uriInfo.getBaseUri() + "classroom/" + newLecture.get().getClassId());
        return Response.created(location).build();
    }

}
