package ar.edu.itba.paw.webapp.exceptions;

public class RegisterErrorException extends RuntimeException {
    public RegisterErrorException(String message) {
        super(message);
    }
}
