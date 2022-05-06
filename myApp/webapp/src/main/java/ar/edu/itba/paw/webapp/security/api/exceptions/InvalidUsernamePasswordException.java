package ar.edu.itba.paw.webapp.security.api.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidUsernamePasswordException extends AuthenticationException {
    public InvalidUsernamePasswordException(String explanation) {
        super(explanation);
    }
}

