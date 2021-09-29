package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Class;
import java.util.List;
import java.util.Optional;

public interface ClassService {
    Class findById(int id);
    List<Class> findClassesByStudentId(int id);
    List<Class> findClassesByTeacherId(int id);
    Optional<Class> create(int studentId, int teacherId, int level, int subjectId, int price, int status);
    int setStatus(int classId, int status);
    int setRequest(int classId, String message);
    int setReply(int classId, String message);
}
