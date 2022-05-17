package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Subject File Not Found")
public class SubjectFileNotFoundException extends RuntimeException {
    public SubjectFileNotFoundException() {
        super("Subject File Not Found");
    }
}
