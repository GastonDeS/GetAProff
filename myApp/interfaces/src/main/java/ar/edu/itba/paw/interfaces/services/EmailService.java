package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Lecture;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);

    void sendNewClassMessage(String to, String userFrom, String subject);

    void sendStatusChangeMessage(Lecture myLecture);

    void sendRatedMessage(Lecture myLecture, int rating, String review);

    void sendSubjectRequest(Long uid, String Subject, String message);

}