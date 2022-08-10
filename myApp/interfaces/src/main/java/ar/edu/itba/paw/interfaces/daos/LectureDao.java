package ar.edu.itba.paw.interfaces.daos;


import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.PageRequest;
import ar.edu.itba.paw.models.SubjectFile;

import java.util.List;
import java.util.Optional;

public interface LectureDao {

    Optional<Lecture> get(Long id);

    Page<Lecture> findClassesByStudentId(Long studentId, PageRequest pageRequest);

    Page<Lecture> findClassesByStudentAndStatus(Long studentId, Integer status, PageRequest pageRequest);

    Page<Lecture> findClassesByStudentAndMultipleStatus(Long studentId, Integer status, PageRequest pageRequest);

    Page<Lecture> findClassesByTeacherId(Long teacherId, PageRequest pageRequest);

    Page<Lecture> findClassesByTeacherAndStatus(Long teacherId, Integer status, PageRequest pageRequest);

    Page<Lecture> findClassesByTeacherAndMultipleStatus(Long teacherId, Integer status, PageRequest pageRequest);

    Lecture create(Long studentId, Long teacherId, int level, Long subjectId, int price);

    int setStatus(Long classId, int status);

    Integer getNotificationsCount( Long classId, int role);

    int refreshTime(Long classId,int role);

    int addSharedFileToLecture(Long subjectFileId, Long lectureId);

    int stopSharingFileInLecture(Long subjectFileId, Long lectureId);

    List<SubjectFile> getSharedFilesByTeacher(Long classId);

}
