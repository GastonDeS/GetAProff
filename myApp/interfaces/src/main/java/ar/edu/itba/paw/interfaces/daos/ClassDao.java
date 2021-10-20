package ar.edu.itba.paw.interfaces.daos;


import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.User;


import java.util.List;
import java.util.Optional;

public interface ClassDao {

    Optional<Class> get(Long id);

    List<Class> findClassesByStudentId(User student);

    List<Class> findClassesByTeacherId(User teacher);

    Class create(User student, User teacher, int level, Subject subject, int price, int status, String message);

    int setStatus(Long classId, int status);

    int setDeleted(Long classId, int deleted);

    int setRequest(Long classId, String message);

    int setReply(Long classId, String message);
}
