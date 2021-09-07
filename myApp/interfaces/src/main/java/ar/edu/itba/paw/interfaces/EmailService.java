package ar.edu.itba.paw.interfaces;

public interface EmailService {
    void sendSimpleMessage(String to,String subject, String text);
    void sendTemplateMessage(String to, String subject,String mailFrom, String message);
}
