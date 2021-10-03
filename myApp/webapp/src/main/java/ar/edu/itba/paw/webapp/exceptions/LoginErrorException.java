package ar.edu.itba.paw.webapp.exceptions;

public class LoginErrorException extends RuntimeException {
    public LoginErrorException(String message) {
        super(message);
    }
}
