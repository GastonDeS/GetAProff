package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.AuthDto;
import ar.edu.itba.paw.webapp.security.services.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/user")
@Component
public class AuthController {
    @Context
    private UriInfo uriInfo;

    @Autowired
    private AuthFacade authFacade;

    @GET
    public Response getUser() {
        User user = authFacade.getCurrentUser();
        return Response.ok(AuthDto.fromUser(uriInfo, user)).build();
    }
}
