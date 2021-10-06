package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.SubjectInfo;
import ar.edu.itba.paw.models.Teaches;

import java.util.List;
import java.util.Optional;

public interface TeachesDao {
    List<Teaches> findUserBySubject(int subjectId);

    Teaches addSubjectToUser(int userid, int subjectid, int price, int level);

    Optional<List<Teaches>> findSubjectByUser(int userid);

    int removeSubjectToUser(int userid, int subjectid);

    Optional<Teaches> findByUserAndSubject(int userId, int subjectId);

    Optional<List<SubjectInfo>> getSubjectInfoListByUser(int userid);
}
