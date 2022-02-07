package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.AuthDto;
import ar.edu.itba.paw.webapp.dto.StudentDto;
import ar.edu.itba.paw.webapp.requestDto.LoginDto;
import ar.edu.itba.paw.webapp.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Path("auth")
@Component
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private JwtUtils jwtUtils;

    @POST
    @Path("/login")
    @Consumes(value = { MediaType.APPLICATION_JSON, })
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response login(@Valid @RequestBody LoginDto loginDto) throws UnsupportedEncodingException {
        final Optional<User> maybeUser = userService.findByEmail(loginDto.getMail());
        if (!maybeUser.isPresent()) return Response.status(Response.Status.NOT_FOUND).build();

        User user = maybeUser.get();
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getMail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = jwtUtils.generateJwtToken(loginDto.getMail());
        return Response.ok(AuthDto.login(uriInfo, user, token)).build();
    }

//    @POST
//    @Path("/register/student")
//    @Consumes(value = { MediaType.APPLICATION_JSON, })
//    @Produces(value = { MediaType.APPLICATION_JSON, })
//    public Response register(@Validated(RegisterForm.Student.class) @RequestBody RegisterDto registerDto) {
//
//    }

}
