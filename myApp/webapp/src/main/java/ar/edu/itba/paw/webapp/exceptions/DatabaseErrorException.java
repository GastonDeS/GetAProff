package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Database Error")
public class DatabaseErrorException extends RuntimeException {
    public DatabaseErrorException() {
        super("Database Error");
    }
}
