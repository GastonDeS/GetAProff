package ar.edu.itba.paw.webapp.exceptions;

public class ClassNotFoundException extends RuntimeException {
    public ClassNotFoundException(String message) {
        super(message);
    }
}
