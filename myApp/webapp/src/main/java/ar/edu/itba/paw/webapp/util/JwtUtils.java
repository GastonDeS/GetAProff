package ar.edu.itba.paw.webapp.util;

import ar.edu.itba.paw.webapp.security.services.implementation.PawUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@PropertySource("classpath:application.properties")
public class JwtUtils {

    @Autowired
    private PawUserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private Integer jwtExpirationMs;

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    public String generateJwtToken(String mail) {
        String token = Jwts
                .builder()
                .setSubject(mail)
                .claim("authorities",
                        userDetailsService.loadUserByUsername(mail).getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512,
                        jwtSecret.getBytes()).compact();
        return "Bearer " + token;
    }

    public Claims validateJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        return Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    public boolean existsJwtToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
    }
}
