package ar.edu.itba.paw.models.exceptions;

public class OperationFailedException extends RuntimeException {
    public OperationFailedException(String message) {
        super(message);
    }
}
