package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Class;

import java.util.List;
import java.util.Optional;

public interface ClassService {
    Optional<Class> findById(Long id);

    List<Class> findClassesByStudentAndStatus(Long studentId, Integer status);

    List<Class> findClassesByTeacherAndStatus(Long teacherId, Integer status);

    Class create(Long studentId, Long teacherId, int level, Long subjectId, int price);

    int setStatus(Long classId, int status);
}
