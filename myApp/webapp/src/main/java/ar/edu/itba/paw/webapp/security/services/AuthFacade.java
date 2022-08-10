package ar.edu.itba.paw.webapp.security.services;

import ar.edu.itba.paw.models.User;

public interface AuthFacade {
    User getCurrentUser();
    Long getCurrentUserId();
    boolean isTeacherUser();
}
