package ar.edu.itba.paw.webapp.security.api.exceptions;

import org.springframework.security.core.AuthenticationException;

public class ExpiredAuthenticationTokenException extends AuthenticationException {
    public ExpiredAuthenticationTokenException(String msg, Throwable t) {
        super(msg, t);
    }
}
