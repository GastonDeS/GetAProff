package ar.edu.itba.paw.webapp.security.api.handlers;

import ar.edu.itba.paw.webapp.security.api.models.BasicAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if(authentication instanceof BasicAuthenticationToken) {
            response.addHeader("Authorization", "Bearer " + ((BasicAuthenticationToken) authentication).getToken());
        }
    }
}
