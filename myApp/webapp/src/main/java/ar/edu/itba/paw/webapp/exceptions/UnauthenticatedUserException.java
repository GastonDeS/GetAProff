package ar.edu.itba.paw.webapp.exceptions;

public class UnauthenticatedUserException extends RuntimeException {

    public UnauthenticatedUserException(String message) {
        super(message);
    }

}
