package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.SubjectInfo;
import ar.edu.itba.paw.models.Teaches;

import java.util.List;
import java.util.Optional;

public interface TeachesDao {

    Teaches addSubjectToUser(Long userId, Long subjectId, int price, int level);

    int removeSubjectToUser(Long userId, Long subjectId, int level);

    Optional<Teaches> findByUserAndSubjectAndLevel(Long userId, Long subjectId, int level);

    List<SubjectInfo> getSubjectInfoListByUser(Long userId);
}
