package ar.edu.itba.paw.interfaces.services;

public interface EmailService {
    void sendSimpleMessage(String to,String subject, String text);
    void sendContactMessage(String to, String mailSubject, String userFrom, String subject, String message);
    void sendAcceptMessage(String to, String mailSubject, String userFrom, String subject,String mailFrom, String message);

}
