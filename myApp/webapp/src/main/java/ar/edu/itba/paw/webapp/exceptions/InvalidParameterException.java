package ar.edu.itba.paw.webapp.exceptions;

public class InvalidParameterException extends RuntimeException{

    public InvalidParameterException(String message) {
        super(message);
    }
}
