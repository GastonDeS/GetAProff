package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.User;

public interface EmailService {

    void sendNewClassMessage(String to, String userFrom, String subject, String localAddr);

    void sendStatusChangeMessage(Lecture myLecture, String localAddr);

    void sendRatedMessage(Lecture myLecture, int rating, String review, String localAddr);

    void sendSubjectRequest(Long uid, String Subject, String message);

    void sendNewPostMessage(User user, Lecture myLecture, String localAddr);
}