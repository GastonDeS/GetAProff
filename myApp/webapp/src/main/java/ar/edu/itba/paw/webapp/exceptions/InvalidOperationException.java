package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Operation invalid")
public class InvalidOperationException extends RuntimeException{
    public InvalidOperationException(String message) {
        super(message);
    }
}
