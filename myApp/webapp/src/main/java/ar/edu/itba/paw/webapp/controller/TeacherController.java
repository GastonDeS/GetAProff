package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.dto.SubjectInfoDto;
import ar.edu.itba.paw.webapp.dto.SubjectLevelDto;
import ar.edu.itba.paw.webapp.dto.TeacherDto;
import ar.edu.itba.paw.webapp.requestDto.EditTeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("teachers")
@Component
public class TeacherController {

    @Autowired
    private TeachesService teachesService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/subject")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response findBySubject(@QueryParam("page") int page, @QueryParam("search") String search) {
        final List<TeacherDto> filteredTeachers = teachesService.findTeachersTeachingSubject(search, page).stream()
                .map(teacherInfo -> TeacherDto.getTeacher(uriInfo, teacherInfo)).collect(Collectors.toList());
        int total = teachesService.getPageQty(search);
        return addPaginationHeaders(page, total, Response.ok(new GenericEntity<List<TeacherDto>>(filteredTeachers){}));
    }

    @GET
    @Path("/filters")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response filterTeachers(@QueryParam("page") int page, @QueryParam("search") String search, @QueryParam("price") Integer price,
                                   @QueryParam("level") Integer level, @QueryParam("rating") Integer rating, @QueryParam("order") Integer order) {
        final List<TeacherDto> filteredTeachers = teachesService.filterUsers(search, order, price, level, rating, page).stream()
                .map(teacherInfo -> TeacherDto.getTeacher(uriInfo, teacherInfo)).collect(Collectors.toList());
        int total = teachesService.getPageQty(search, price, level, rating);
        return addPaginationHeaders(page, total, Response.ok(new GenericEntity<List<TeacherDto>>(filteredTeachers){}));
    }

    private Response addPaginationHeaders(int current, int total, Response.ResponseBuilder response) {
        if (current + 1 <= total) {
            response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", current + 1).build().toString(), "next");
        }
        if (current - 1 > 0) {
            response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", current - 1).build().toString(), "prev");
        }
        if (total > 1) {
            response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", total).build().toString(), "last");
        }
        response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).build().toString(), "first");
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getTeacherInfo(@PathParam("id") Long id) {
        final Optional<TeacherInfo> teacherInfo = teachesService.getTeacherInfo(id);
        return teacherInfo.isPresent() ? Response.ok(TeacherDto.getTeacher(uriInfo, teacherInfo.get())).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/favourites/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getUserFavourites(@PathParam("id") Long id) {
        final List<TeacherDto> favourites = userService.getFavourites(id).stream()
                .map(teacherInfo -> TeacherDto.getTeacher(uriInfo, teacherInfo)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<TeacherDto>>(favourites){}).build();
    }

    @GET
    @Path("/top-rated")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response listTopRatedTeachers() {
        final List<TeacherDto> topRatedTeachers = teachesService.getTopRatedTeachers().stream()
                .map(teacherInfo -> TeacherDto.getTeacher(uriInfo, teacherInfo)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<TeacherDto>>(topRatedTeachers){}).build();
    }

    @GET
    @Path("/most-requested")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response listMostRequestedTeachers() {
        final List<TeacherDto> mostRequestedTeachers = teachesService.getMostRequested().stream()
                .map(teacherInfo -> TeacherDto.getTeacher(uriInfo, teacherInfo)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<TeacherDto>>(mostRequestedTeachers){}).build();
    }

    @GET
    @Path("/subjects/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getSubjectInfoFromUser(@PathParam("id") Long id) {
        final List<SubjectInfoDto> subjectInfoDtos = teachesService.get(id).stream()
                .map(SubjectInfoDto::fromSubjectInfo).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<SubjectInfoDto>>(subjectInfoDtos){}).build();
    }

    @GET
    @Path("/subjects/levels/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getSubjectsAndLevelsTaughtByUser(@PathParam("id") Long id) {
        final List<SubjectLevelDto> subjectLevelDtos = teachesService.getSubjectAndLevelsTaughtByUser(id)
                .entrySet().stream().map(entry -> SubjectLevelDto.fromSubjectLevel(uriInfo, entry)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<SubjectLevelDto>>(subjectLevelDtos){}).build();
    }

    @DELETE
    @Path("/{userId}/{id}/{level}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response removeSubjectsTaughtFromUser(@PathParam("userId") Long userId, @PathParam("id") Long id, @PathParam("level") int level) {
        return teachesService.removeSubjectToUser(userId, id, level) == 1 ?
                Response.status(Response.Status.OK).build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("/update")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response editTeacherProfile(EditTeacherDto editTeacherDto) {
        boolean added = false;
        if (editTeacherDto.isSwitchRole()) {
            added = userRoleService.addRoleToUser(editTeacherDto.getId(), Roles.TEACHER.getId());
        }
        userService.setTeacherAuthorityToUser();
        int desc = userService.setUserDescription(editTeacherDto.getId(), editTeacherDto.getDescription());
        int sch = userService.setUserSchedule(editTeacherDto.getId(), editTeacherDto.getSchedule());
        int name = userService.setUserName(editTeacherDto.getId(), editTeacherDto.getName());
        //TODO: EXCEPTION
        return (added && desc == 1 && sch == 1 && name == 1) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/available-subjects/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getSubjectAndLevelsAvailableForUser(@PathParam("id") Long id) {
        final List<SubjectLevelDto> subjectLevelDtos = teachesService.getSubjectAndLevelsAvailableForUser(id)
                .entrySet().stream().map(entry -> SubjectLevelDto.fromSubjectLevel(uriInfo, entry)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<SubjectLevelDto>>(subjectLevelDtos){}).build();
    }

    @POST
    @Path("/{userId}/{subjectId}/{price}/{level}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response addSubjectToUser(@PathParam("userId") Long userId, @PathParam("subjectId") Long subjectId,
                                     @PathParam("price") int price, @PathParam("level") int level) {
        final Optional<Teaches> newTeaches = teachesService.addSubjectToUser(userId, subjectId, price, level);
        return newTeaches.isPresent() ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }
}
