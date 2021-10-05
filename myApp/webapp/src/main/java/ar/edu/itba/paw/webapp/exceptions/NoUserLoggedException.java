package ar.edu.itba.paw.webapp.exceptions;

public class NoUserLoggedException extends RuntimeException {
    public NoUserLoggedException(String message) {
        super(message);
    }
}
