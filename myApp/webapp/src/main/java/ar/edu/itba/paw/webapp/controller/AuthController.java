package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.StudentDto;
import ar.edu.itba.paw.webapp.security.api.models.Authority;
import ar.edu.itba.paw.webapp.dto.AuthDto;
import ar.edu.itba.paw.webapp.security.services.AuthFacade;
import ar.edu.itba.paw.webapp.security.services.AuthenticationTokenService;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Path("/auth")
@Component
public class AuthController {
    @Autowired
    AuthenticationTokenService authenticationTokenService;

    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AuthFacade authFacade;

    @GET
    @Path("/login")
    public Response login() {
        User user = authFacade.getCurrentUser();
        return Response.ok(AuthDto.fromUser(uriInfo, user)).build();
    }

    @POST
    @Consumes(value = { MediaType.MULTIPART_FORM_DATA })
    public Response register(@FormDataParam("name") String name, @FormDataParam("mail") String mail,
                             @FormDataParam("password") String password, @FormDataParam("description") String description, @FormDataParam("role") Long role,
                             @FormDataParam("schedule") String schedule) throws IOException {
        Optional<User> mayBeUser = userService.findByEmail(mail);
        if(mayBeUser.isPresent())
            return Response.status(Response.Status.BAD_REQUEST).build();
        Optional<User> newUser = userService.create(name, mail, password, description, schedule, role);
        if(!newUser.isPresent())
            return Response.status(Response.Status.CONFLICT).build();
        URI location = URI.create(uriInfo.getAbsolutePath() + "/" + newUser.get().getId());
        Response.ResponseBuilder response = Response.created(location);
        response.entity(AuthDto.fromUser(uriInfo, newUser.get()));
        Set<Authority> authoritySet = new HashSet<>();
        authoritySet.add(Authority.USER_STUDENT);
        if (newUser.get().isTeacher()) authoritySet.add(Authority.USER_TEACHER);
        return response.header("Authorization", authenticationTokenService.issueToken(newUser.get().getMail(), authoritySet)).build();
    }

}
