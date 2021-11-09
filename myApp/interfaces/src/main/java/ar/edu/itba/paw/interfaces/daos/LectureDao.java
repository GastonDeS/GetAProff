package ar.edu.itba.paw.interfaces.daos;


import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.SubjectFile;

import java.util.List;
import java.util.Optional;

public interface LectureDao {

    Optional<Lecture> get(Long id);

    List<Lecture> findClassesByStudentId(Long studentId);

    List<Lecture> findClassesByStudentAndStatus(Long studentId, Integer status);

    List<Lecture> findClassesByStudentAndMultipleStatus(Long studentId, Integer status);

    List<Lecture> findClassesByTeacherId(Long teacherId);

    List<Lecture> findClassesByTeacherAndStatus(Long teacherId, Integer status);

    List<Lecture> findClassesByTeacherAndMultipleStatus(Long teacherId, Integer status);

    Lecture create(Long studentId, Long teacherId, int level, Long subjectId, int price);

    int setStatus(Long classId, int status);

    public Integer getNotificationsCount( Long classId, int role);

    void refreshTime(Long classId,int role);

    int addSharedFileToLecture(Long subjectFileId, Long lectureId);

    List<SubjectFile> getSharedFilesByTeacher(Long classId);

}
