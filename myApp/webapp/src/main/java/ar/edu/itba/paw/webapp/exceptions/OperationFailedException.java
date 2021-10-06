package ar.edu.itba.paw.webapp.exceptions;

public class OperationFailedException extends RuntimeException {

    public OperationFailedException(String message) {
        super(message);
    }

}
