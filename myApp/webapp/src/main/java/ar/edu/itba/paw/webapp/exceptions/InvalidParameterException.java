package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid parameter")
public class InvalidParameterException extends RuntimeException{
    public InvalidParameterException() {
        super("Invalid parameter");
    }

    public InvalidParameterException(String message) {
        super(message);
    }
}
