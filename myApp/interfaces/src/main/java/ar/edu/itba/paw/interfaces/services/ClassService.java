package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.ClassInfo;

import java.util.List;
import java.util.Optional;

public interface ClassService {
    Class findById(int id);
    List<ClassInfo> findClassesByStudentId(int id);
    List<ClassInfo> findClassesByTeacherId(int id);
    Optional<Class> create(int studentId, int teacherId, int level, int subjectId, int price, int status);
    int setStatus(int classId, int status);
    int setRequest(int classId, String message);
    int setReply(int classId, String message);
}
