package ar.edu.itba.paw.interfaces.daos;


import ar.edu.itba.paw.models.Class;

import java.util.List;
import java.util.Optional;

public interface ClassDao {

    Optional<Class> get(Long id);

    List<Class> findClassesByStudentId(Long studentId);

    List<Class> findClassesByStudentAndStatus(Long studentId, Integer status);

    List<Class> findClassesByStudentAndMultipleStatus(Long studentId, Integer status);

    List<Class> findClassesByTeacherId(Long teacherId);

    List<Class> findClassesByTeacherAndStatus(Long teacherId, Integer status);

    List<Class> findClassesByTeacherAndMultipleStatus(Long teacherId, Integer status);

    Class create(Long studentId, Long teacherId, int level, Long subjectId, int price, int status, String message);

    int setStatus(Long classId, int status);

    int setDeleted(Long classId, int deleted);

    int setRequest(Long classId, String message);

    int setReply(Long classId, String message);
}
