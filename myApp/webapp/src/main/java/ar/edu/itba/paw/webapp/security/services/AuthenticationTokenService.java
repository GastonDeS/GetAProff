package ar.edu.itba.paw.webapp.security.services;

import ar.edu.itba.paw.webapp.security.api.models.AuthenticationTokenDetails;
import ar.edu.itba.paw.webapp.security.api.models.Authority;

import java.util.Set;

public interface AuthenticationTokenService {
    String issueToken(String username, Set<Authority> authorities);
    AuthenticationTokenDetails parseToken(String token);
    String refreshToken(AuthenticationTokenDetails currentAuthenticationTokenDetails);
}

