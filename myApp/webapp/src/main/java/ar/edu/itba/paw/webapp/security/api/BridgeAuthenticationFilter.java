package ar.edu.itba.paw.webapp.security.api;

import ar.edu.itba.paw.webapp.security.api.models.BasicAuthenticationToken;
import ar.edu.itba.paw.webapp.security.api.models.JwtAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class BridgeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final int BASIC_TOKEN_OFFSET = 6;
    private static final int JWT_TOKEN_OFFSET = 7;

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return super.requiresAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

    public BridgeAuthenticationFilter() {
        super("/api/");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String header = request.getHeader("Authorization");
        header = header != null ? header : "";
        if (header.startsWith("Bearer ")) {
            String authToken = header.substring(JWT_TOKEN_OFFSET);
            return getAuthenticationManager().authenticate(new JwtAuthenticationToken(authToken));
        }
        else if(header.startsWith("Basic ")){
            String basicToken = header.substring(BASIC_TOKEN_OFFSET);
            return getAuthenticationManager().authenticate(new BasicAuthenticationToken(basicToken));
        }
        throw new InsufficientAuthenticationException("No authorization credentials were provided.");
    }
}
