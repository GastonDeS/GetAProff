package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Post File Not Found")
public class PostFileNotFoundException extends RuntimeException {
    public PostFileNotFoundException() {
        super("Post File Not Found");
    }
}
