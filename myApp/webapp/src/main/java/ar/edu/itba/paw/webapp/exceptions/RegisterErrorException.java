package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Registration failed")
public class RegisterErrorException extends RuntimeException {
    public RegisterErrorException() {
        super("Registration failed");
    }

    public RegisterErrorException(String message) {
        super(message);
    }
}
