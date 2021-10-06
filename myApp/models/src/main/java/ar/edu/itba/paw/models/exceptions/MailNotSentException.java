package ar.edu.itba.paw.models.exceptions;

public class MailNotSentException extends RuntimeException{
    public MailNotSentException (String message) {
        super(message);
    }
}
