package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import ar.edu.itba.paw.webapp.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;


@Controller
@Path("api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private PawUserDetailsService userDetailsService;

    @POST
    @Path("/login")
    @Consumes(value = MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response login(@FormParam("mail") String mail) throws UnsupportedEncodingException {
        final Optional<User> maybeUser = userService.findByEmail(mail);

        if (!maybeUser.isPresent()) return Response.status(Response.Status.NOT_FOUND).build();

        User user = maybeUser.get();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getMail());
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getMail(), user.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = getJWTToken(mail);
        return Response.ok(UserDto.login(uriInfo, user, token)).build();
    }

    private String getJWTToken(String mail) {
        String secretKey = "mySecretKey";

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(mail)
                .claim("authorities",
                        userDetailsService.loadUserByUsername(mail).getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
