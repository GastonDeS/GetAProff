package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User Not Found Exception")
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User Not Found Exception");
    }
}
