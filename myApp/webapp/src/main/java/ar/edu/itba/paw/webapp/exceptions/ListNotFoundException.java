package ar.edu.itba.paw.webapp.exceptions;

public class ListNotFoundException extends RuntimeException{
    public ListNotFoundException(String message) {
        super(message);
    }
}
