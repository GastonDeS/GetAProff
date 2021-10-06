package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.ClassInfo;

import java.util.List;
import java.util.Optional;

public interface ClassService {
    Optional<Class> findById(int id);

    Optional<List<ClassInfo>> findClassesByStudentId(int id);

    Optional<List<ClassInfo>> findClassesByTeacherId(int id);

    Class create(int studentId, int teacherId, int level, int subjectId, int price, int status, String message);

    int setStatus(int classId, int status);

    int setDeleted(int classId, int deleted);

    int setRequest(int classId, String message);

    int setReply(int classId, String message);
}
