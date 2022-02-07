package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Optional;

@Path("students")
@Component
public class StudentController {

    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getUser(@PathParam("id") Long id) {
        final Optional<User> user = userService.findById(id);
        return user.isPresent() ? Response.ok(StudentDto.fromUser(uriInfo, user.get())).build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
