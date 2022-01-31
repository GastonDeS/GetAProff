package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.dto.TeacherDto;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;

@Path("api/teachers")
@Component
public class TeacherController {

    @Autowired
    private TeachesService teachesService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("top-rated")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response listTopRatedTeachers() {
        List<TeacherDto> topRatedTeachers = teachesService.getTopRatedTeachers().stream()
                .map(TeacherDto::getTeacher).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<TeacherDto>>(topRatedTeachers){}).build();
    }

    @GET
    @Path("most-requested")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response listMostRequestedTeachers() {
        List<TeacherDto> mostRequestedTeachers = teachesService.getMostRequested().stream()
                .map(TeacherDto::getTeacher).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<TeacherDto>>(mostRequestedTeachers){}).build();
    }
}
