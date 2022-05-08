package ar.edu.itba.paw.webapp.security.voters;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.security.api.models.BasicAuthenticationToken;
import ar.edu.itba.paw.webapp.security.models.PawUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class AntMatcherVoter {

    @Autowired
    private UserService userService;

    private Long getUserId(Authentication authentication) {
        return getUser(authentication).getUserid();
    }

    private boolean isTeacher(Authentication authentication) {
        return getUser(authentication).isTeacher();
    }

    private User getUser(Authentication authentication) {
        if(authentication instanceof BasicAuthenticationToken) {
            return userService.findByEmail(((BasicAuthenticationToken)authentication).getPrincipal()).orElseThrow(NoSuchElementException::new);
        }
        return ((PawUser)(authentication.getPrincipal())).toUser();
    }


}

