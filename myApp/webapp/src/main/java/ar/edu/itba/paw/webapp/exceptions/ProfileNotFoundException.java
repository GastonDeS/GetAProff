package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Profile Not Found")
public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException() {
        super("Profile Not Found");
    }

    public ProfileNotFoundException(String message) {
        super(message);
    }
}

