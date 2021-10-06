package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.SubjectInfo;
import ar.edu.itba.paw.models.Teaches;

import java.util.List;
import java.util.Optional;

public interface TeachesService {
    Optional<Teaches> addSubjectToUser(int userid, int subjectid, int price, int level);

    int removeSubjectToUser(int userid, int subjectid);

    List<SubjectInfo> getSubjectInfoListByUser(int userid);

    Optional<Teaches> findByUserAndSubjectAndLevel(int userId, int subjectId, int level);
}
