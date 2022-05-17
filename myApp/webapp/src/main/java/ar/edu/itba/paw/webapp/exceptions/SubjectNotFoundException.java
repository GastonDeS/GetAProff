package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Subject Not Found")
public class SubjectNotFoundException extends RuntimeException {
    public SubjectNotFoundException() {
        super("Subject File Not Found");
    }
}
