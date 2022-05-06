package ar.edu.itba.paw.webapp.security.services.implementation;

import ar.edu.itba.paw.webapp.security.api.models.AuthenticationTokenDetails;
import ar.edu.itba.paw.webapp.security.api.models.Authority;
import ar.edu.itba.paw.webapp.security.services.AuthenticationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Component
public class AuthenticationTokenServiceImpl implements AuthenticationTokenService {

    @Value("${jwt.validFor}")
    private Long validFor;

    @Autowired
    TokenIssuer tokenIssuer;

    @Autowired
    TokenParser tokenParser;

    @Override
    public String issueToken(String username, Set<Authority> authorities) {
        String id = UUID.randomUUID().toString();
        ZonedDateTime issuedDate = ZonedDateTime.now();
        ZonedDateTime expirationDate = issuedDate.plusSeconds(validFor);

        AuthenticationTokenDetails authenticationTokenDetails = new AuthenticationTokenDetails.Builder()
                .withId(id)
                .withUsername(username)
                .withAuthorities(authorities)
                .withIssuedDate(issuedDate)
                .withExpirationDate(expirationDate)
                .build();
        return tokenIssuer.issueToken(authenticationTokenDetails);
    }

    @Override
    public AuthenticationTokenDetails parseToken(String token) {
        return tokenParser.parseToken(token);
    }

    @Override
    public String refreshToken(AuthenticationTokenDetails currentAuthenticationTokenDetails) {
        ZonedDateTime issuedDate = ZonedDateTime.now();
        ZonedDateTime expirationDate = issuedDate.plusSeconds(validFor);

        AuthenticationTokenDetails newTokenDetails = new AuthenticationTokenDetails.Builder()
                .withId(currentAuthenticationTokenDetails.getId())
                .withUsername(currentAuthenticationTokenDetails.getUsername())
                .withAuthorities(currentAuthenticationTokenDetails.getAuthorities())
                .withIssuedDate(issuedDate)
                .withExpirationDate(expirationDate)
                .build();

        return tokenIssuer.issueToken(newTokenDetails);
    }
}
