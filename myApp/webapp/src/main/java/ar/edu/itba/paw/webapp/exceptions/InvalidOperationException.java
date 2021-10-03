package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.web.servlet.ModelAndView;

public class InvalidOperationException extends RuntimeException {

    private String redirect;

    public InvalidOperationException(String message) {
        super(message);
    }

    public InvalidOperationException(String message, String redirect) {
        super(message);
        this.redirect = redirect;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}
