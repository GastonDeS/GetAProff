package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.TeacherInfo;
import ar.edu.itba.paw.webapp.dto.IdDto;
import ar.edu.itba.paw.webapp.dto.LinkUserDto;
import ar.edu.itba.paw.webapp.dto.TeacherDto;
import ar.edu.itba.paw.webapp.security.services.AuthFacade;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/favourites")
@Component
public class FavoritesController {
    private static final int ALREADY_INSERTED = 0, NO_CONTENT_TO_DELETE = 0; // TODO transform this into exceptions

    @Autowired
    private UserService userService;

    @Autowired
    private AuthFacade authFacade;

    @Context
    private UriInfo uriInfo;

    //Return all favorite users of user with uid
    @GET
    @Produces({"application/vnd.getaproff.api.v1+json"})
    public Response getUserFavorites(@QueryParam("page") @DefaultValue("1") Integer page,
                                     @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        try {
            final Page<TeacherInfo> favourites = userService.getFavourites(authFacade.getCurrentUserId(), page, pageSize);
            Response.ResponseBuilder builder = Response.ok(
                    new GenericEntity<List<TeacherDto>>(favourites.getContent().stream()
                            .map(teacherInfo -> TeacherDto.getTeacher(uriInfo, teacherInfo))
                            .collect(Collectors.toList())) {
                    });
            return PaginationBuilder.build(favourites, builder, uriInfo, pageSize);
        } catch (IllegalArgumentException exception) { // TODO mensaje exacto
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{teacherId}")
    @Produces({"application/vnd.getaproff.api.v1+json"})
    public Response getFavedTeacher(@PathParam("teacherId") Long teacherId) {
        boolean isFaved = userService.isFaved(teacherId, authFacade.getCurrentUserId());
        System.out.println(isFaved);
        if (!isFaved) return Response.status(Response.Status.NO_CONTENT).build();
        return Response.ok(LinkUserDto.fromUserId(uriInfo, teacherId.toString())).build();
    }

    @POST
    @Path("/{teacherId}")
    @Produces({"application/vnd.getaproff.api.v1+json"})
    public Response addNewFavoriteUser(@PathParam("teacherId") Long teacherId) {
        int result = userService.addFavourite(teacherId, authFacade.getCurrentUserId());
        if (result == ALREADY_INSERTED) return Response.status(Response.Status.NO_CONTENT).build();
        return Response.ok().build();
    }

    @DELETE
    @Path("/{teacherId}")
    public Response removeFavoriteUser(@PathParam("teacherId") Long teacherId) {
        int result = userService.removeFavourite(teacherId, authFacade.getCurrentUserId());
        if (result == NO_CONTENT_TO_DELETE)
            return Response.status(Response.Status.NO_CONTENT).build();
        return Response.ok().build();
    }
}
