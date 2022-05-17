package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Certification Not Found Exception")
public class CertificationNotFoundException extends RuntimeException{
    public CertificationNotFoundException() {
        super("CertificationNotFoundException");
    }
}
