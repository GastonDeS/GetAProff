package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Post Not Found")
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super("Post Not Found");
    }
}
