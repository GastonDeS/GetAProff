package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Must be authenticated")
public class NoUserLoggedException extends RuntimeException {
    public NoUserLoggedException() {
        super("Must be authenticated");
    }

    public NoUserLoggedException(String message) {
        super(message);
    }
}
