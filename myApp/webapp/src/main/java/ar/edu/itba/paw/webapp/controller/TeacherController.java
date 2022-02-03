package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.dto.TeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("api/teachers")
@Component
public class TeacherController {

    @Autowired
    private TeachesService teachesService;

    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getTeacherInfo(@PathParam("id") Long id) {
        Optional<TeacherInfo> teacherInfo = teachesService.getTeacherInfo(id);
        return teacherInfo.isPresent() ? Response.ok(TeacherDto.getTeacher(uriInfo, teacherInfo.get())).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/favourites/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getUserFavourites(@PathParam("id") Long id) {
        List<TeacherDto> favourites = userService.getFavourites(id).stream()
                .map(teacherInfo -> TeacherDto.getTeacher(uriInfo, teacherInfo)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<TeacherDto>>(favourites){}).build();
    }

    @GET
    @Path("/top-rated")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response listTopRatedTeachers() {
        List<TeacherDto> topRatedTeachers = teachesService.getTopRatedTeachers().stream()
                .map(teacherInfo -> TeacherDto.getTeacher(uriInfo, teacherInfo)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<TeacherDto>>(topRatedTeachers){}).build();
    }

    @GET
    @Path("/most-requested")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response listMostRequestedTeachers() {
        List<TeacherDto> mostRequestedTeachers = teachesService.getMostRequested().stream()
                .map(teacherInfo -> TeacherDto.getTeacher(uriInfo, teacherInfo)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<TeacherDto>>(mostRequestedTeachers){}).build();
    }
}
