package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.TeacherInfo;
import ar.edu.itba.paw.webapp.dto.LinkUserDto;
import ar.edu.itba.paw.webapp.dto.TeacherDto;
import ar.edu.itba.paw.webapp.exceptions.ConflictException;
import ar.edu.itba.paw.webapp.exceptions.NoContentException;
import ar.edu.itba.paw.webapp.security.services.AuthFacade;
import ar.edu.itba.paw.webapp.util.ConflictStatusMessages;
import ar.edu.itba.paw.webapp.util.NoContentStatusMessages;
import ar.edu.itba.paw.webapp.util.PaginationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/favourites")
@Controller
public class FavoritesController {
    private static final int SUCCESS = 1;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthFacade authFacade;

    @Context
    private UriInfo uriInfo;

    //Return all favorite users of user with uid
    @GET
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response getUserFavorites(@QueryParam("page") @DefaultValue("1") Integer page,
                                     @QueryParam("pageSize") @DefaultValue("10") Integer pageSize) {
        final Page<TeacherInfo> favourites = userService.getFavourites(authFacade.getCurrentUserId(), page, pageSize);
        Response.ResponseBuilder builder = Response.ok(
                new GenericEntity<List<TeacherDto>>(favourites.getContent().stream()
                        .map(TeacherDto::getTeacher)
                        .collect(Collectors.toList())) {
                });
        return PaginationBuilder.build(favourites, builder, uriInfo, pageSize);
    }

    @GET
    @Path("/{teacherId}")
    @Produces({"application/vnd.getaproff.api.v1+json"})
    public Response getFavedTeacher(@PathParam("teacherId") Long teacherId) {
        boolean isFaved = userService.isFaved(teacherId, authFacade.getCurrentUserId());
        if (!isFaved) throw new NoContentException(NoContentStatusMessages.FAVORITE);;
        return Response.ok(LinkUserDto.fromUserId(teacherId.toString())).build();
    }

    @POST
    @Path("/{teacherId}")
    @Produces("application/vnd.getaproff.api.v1+json")
    public Response addNewFavoriteUser(@PathParam("teacherId") Long teacherId) {
        int result = userService.addFavourite(teacherId, authFacade.getCurrentUserId());
        if (result != SUCCESS) throw new ConflictException(ConflictStatusMessages.FAVORITE);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{teacherId}")
    public Response removeFavoriteUser(@PathParam("teacherId") Long teacherId) {
        int result = userService.removeFavourite(teacherId, authFacade.getCurrentUserId());
        if (result != SUCCESS) throw new NoContentException(NoContentStatusMessages.FAVORITE);
        return Response.ok().build();
    }
}
