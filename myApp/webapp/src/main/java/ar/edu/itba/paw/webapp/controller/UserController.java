package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Optional;

@Path("api/users")
@Component
public class UserController {
    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getUser(@PathParam("id") Long id) {
        final Optional<User> user = userService.findById(id);
        return user.isPresent() ? Response.ok(UserDto.fromUser(uriInfo, user.get())).build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
