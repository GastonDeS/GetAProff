package ar.edu.itba.paw.webapp.security.services.implementation;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.security.api.models.BasicAuthenticationToken;
import ar.edu.itba.paw.webapp.security.models.PawUser;
import ar.edu.itba.paw.webapp.security.services.AuthFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class AuthFacadeImpl implements AuthFacade {

    @Autowired
    private UserService userService;

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!isAuthenticationBasic()) {
            return ((PawUser)(authentication.getPrincipal())).toUser();
        }
        String username = (String) authentication.getPrincipal();
        return userService.findByEmail(username).orElseThrow(NoSuchElementException::new);
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getUserid();
    }

    private boolean isAuthenticationBasic() {
        return SecurityContextHolder.getContext().getAuthentication() instanceof BasicAuthenticationToken;
    }

    public boolean isTeacherUser() {
        return getCurrentUser().isTeacher();
    }
}
