package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.Rating;

import java.util.Optional;

public interface EmailService {

    void sendNewClassMessage(String to, String userFrom, String subject, long classId,String localAddr);

    void sendStatusChangeMessage(Lecture myLecture, int status,String localAddr);

    void sendRatedMessage(long teacherId, long studentId, Rating rating, String localAddr);

    void sendSubjectRequest(Long uid, String Subject, String message);

    void sendNewPostMessage(long posterId, Lecture myLecture, String localAddr);
}