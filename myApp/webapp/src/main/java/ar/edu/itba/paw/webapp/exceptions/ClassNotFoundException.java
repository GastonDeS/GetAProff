package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Class Not Found")
public class ClassNotFoundException extends RuntimeException {
    public ClassNotFoundException() {
        super("Class Not Found");
    }

    public ClassNotFoundException(String message) {
        super(message);
    }
}
