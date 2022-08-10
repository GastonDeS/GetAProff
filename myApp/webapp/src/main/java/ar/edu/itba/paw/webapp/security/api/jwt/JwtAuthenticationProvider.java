package ar.edu.itba.paw.webapp.security.api.jwt;

import ar.edu.itba.paw.webapp.security.api.models.AuthenticationTokenDetails;
import ar.edu.itba.paw.webapp.security.api.models.JwtAuthenticationToken;
import ar.edu.itba.paw.webapp.security.services.AuthenticationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    AuthenticationTokenService authenticationTokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        AuthenticationTokenDetails authenticationTokenDetails = authenticationTokenService.parseToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationTokenDetails.getUsername());
        return new JwtAuthenticationToken(userDetails, authenticationTokenDetails, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
