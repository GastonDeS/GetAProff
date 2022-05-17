package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Image Not Found Exception")
public class ImageNotFoundException extends RuntimeException{
    public ImageNotFoundException() {
        super("Image Not Found Exception");
    }
}
