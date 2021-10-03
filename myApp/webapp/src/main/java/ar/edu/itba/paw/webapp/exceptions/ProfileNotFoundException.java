package ar.edu.itba.paw.webapp.exceptions;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(String message) {
        super(message);
    }
}

