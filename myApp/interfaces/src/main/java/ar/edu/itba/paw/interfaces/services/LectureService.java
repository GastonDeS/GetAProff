package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Lecture;

import java.util.List;
import java.util.Optional;

public interface LectureService {
    Optional<Lecture> findById(Long id);

    List<Lecture> findClassesByStudentAndStatus(Long studentId, Integer status);

    List<Lecture> findClassesByTeacherAndStatus(Long teacherId, Integer status);

    Lecture create(Long studentId, Long teacherId, int level, Long subjectId, int price);

    int setStatus(Long classId, int status);

    Integer getNotificationsCount( Long classId, int role) ;

    void refreshTime(Long classId, int role);
}
