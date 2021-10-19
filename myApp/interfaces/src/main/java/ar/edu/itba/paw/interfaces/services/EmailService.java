package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Class;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);

    void sendContactMessage(String to, String userFrom, String subject, String message);

    void sendAcceptMessage(Long toId, Long fromId, Long sid, String message);

    void sendStatusChangeMessage(Class myClass);

    void sendRatedMessage(Class myClass, int rating, String review);

    void sendSubjectRequest(Long uid, String Subject, String message);

}