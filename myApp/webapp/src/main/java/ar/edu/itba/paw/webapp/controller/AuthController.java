package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.requestDto.NewUserDto;
import ar.edu.itba.paw.webapp.security.api.models.Authority;
import ar.edu.itba.paw.webapp.dto.AuthDto;
import ar.edu.itba.paw.webapp.security.services.AuthFacade;
import ar.edu.itba.paw.webapp.security.services.AuthenticationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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
    private AuthFacade authFacade;

    @GET
    @Path("/login")
    public Response login() {
        User user = authFacade.getCurrentUser();
        return Response.ok(AuthDto.fromUser(uriInfo, user)).build();
    }

    @POST@Path("/teacher")
    @Consumes("application/vnd.getaproff.api.v1+json")
    public Response registerTeacher(@Validated(NewUserDto.Teacher.class) @RequestBody NewUserDto newUserDto) {
        return commonRegister(newUserDto);
    }

    @POST@Path("/student")
    @Consumes("application/vnd.getaproff.api.v1+json")
    public Response registerStudent(@Validated(NewUserDto.Student.class) @RequestBody NewUserDto newUserDto) {
        return commonRegister(newUserDto);
    }

    private Response commonRegister(NewUserDto newUserDto) {
        Optional<User> mayBeUser = userService.findByEmail(newUserDto.getMail());
        if(mayBeUser.isPresent())
            return Response.status(Response.Status.BAD_REQUEST).build();
        Optional<User> newUser = userService.create(newUserDto.getName(), newUserDto.getMail(), newUserDto.getPassword(),
                newUserDto.getDescription(), newUserDto.getSchedule(), newUserDto.getRole());
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
