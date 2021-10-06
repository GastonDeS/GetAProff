package ar.edu.itba.paw.webapp.exceptions;

public class InvalidOperationException extends RuntimeException{

    public InvalidOperationException(String message) {
        super(message);
    }
}
