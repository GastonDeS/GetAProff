package ar.edu.itba.paw.interfaces.daos;


import ar.edu.itba.paw.models.Class;

import java.util.List;

public interface ClassDao {
    Class get(int id);
    List<Class> findClassesByStudentId(int id);
    List<Class> findClassesByTeacherId(int id);
    Class create(int studentId, int teacherId, int level, int subjectId, int price, int status);
    int setStatus(int classId, int status);
}
