package ar.edu.itba.paw.interfaces;

public interface EmailService {
    void sendSimpleMessage(String to,String subject, String text);
    void sendTemplateMessage(String to, String mailSubject, String userFrom, String subject,String mailFrom, String message);
}
