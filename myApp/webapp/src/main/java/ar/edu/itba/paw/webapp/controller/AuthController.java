package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.AuthDto;
import ar.edu.itba.paw.webapp.security.services.AuthFacade;
import ar.edu.itba.paw.webapp.util.JwtUtils;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
import java.util.Optional;

@Path("/auth")
@Component
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

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

    //TODO: sacar este endpoint
//    @POST
//    @Path("/login")
//    @Consumes(value = { MediaType.APPLICATION_JSON, })
//    @Produces(value = { MediaType.APPLICATION_JSON, })
//    public Response login(@Valid @RequestBody LoginDto loginDto) throws UnsupportedEncodingException {
//        final Optional<User> maybeUser = userService.findByEmail(loginDto.getMail());
//        if (!maybeUser.isPresent()) return Response.status(Response.Status.NOT_FOUND).build();
//
//        User user = maybeUser.get();
//        Authentication auth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginDto.getMail(), loginDto.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//        String token = jwtUtils.generateJwtToken(loginDto.getMail());
//        return Response.ok(AuthDto.login(uriInfo, user, token)).build();
//    }

    @POST
    @Consumes(value = { MediaType.MULTIPART_FORM_DATA })
    public Response register(@FormDataParam("name") String name, @FormDataParam("mail") String mail,
                             @FormDataParam("password") String password, @FormDataParam("description") String description, @FormDataParam("role") Long role,
                             @FormDataParam("schedule") String schedule, @FormDataParam("image") InputStream image) throws IOException {
        Optional<User> mayBeUser = userService.findByEmail(mail);
        if(mayBeUser.isPresent())
            return Response.status(Response.Status.BAD_REQUEST).build();
        Optional<User> newUser = userService.create(name, mail, password, description, schedule, role);
        if(!newUser.isPresent())
            return Response.status(Response.Status.CONFLICT).build();
        Optional<Image> maybeImage = imageService.createOrUpdate(newUser.get().getId(), IOUtils.toByteArray(image));
        if(!maybeImage.isPresent())
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        URI location = URI.create(uriInfo.getAbsolutePath() + "/" + newUser.get().getId());
        return Response.created(location).build();
    }

}
