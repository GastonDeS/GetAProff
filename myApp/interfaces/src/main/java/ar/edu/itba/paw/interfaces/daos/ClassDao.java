package ar.edu.itba.paw.interfaces.daos;


import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.ClassInfo;

import java.util.List;

public interface ClassDao {
    Class get(int id);
    List<ClassInfo> findClassesByStudentId(int id);
    List<ClassInfo> findClassesByTeacherId(int id);
    Class create(int studentId, int teacherId, int level, int subjectId, int price, int status, String message);
    int setStatus(int classId, int status);
    int setRequest(int classId, String message);
    int setReply(int classId, String message);
}
