package ar.edu.itba.paw.interfaces.daos;


import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.ClassInfo;

import java.util.List;
import java.util.Optional;

public interface ClassDao {

    Optional<Class> get(Long id);

    List<ClassInfo> findClassesByStudentId(Long id);

    List<ClassInfo> findClassesByTeacherId(Long id);

    Class create(Long studentId, Long teacherId, int level, Long subjectId, int price, int status, String message);

    int setStatus(Long classId, int status);

    int setDeleted(Long classId, int deleted);

    int setRequest(Long classId, String message);

    int setReply(Long classId, String message);
}
