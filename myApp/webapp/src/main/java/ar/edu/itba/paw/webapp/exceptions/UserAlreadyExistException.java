package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User Already Exist")
public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("User Already Exist");
    }
}
