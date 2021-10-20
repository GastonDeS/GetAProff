package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.SubjectInfo;
import ar.edu.itba.paw.models.Teaches;

import java.util.List;
import java.util.Optional;

public interface TeachesService {
    Optional<Teaches> addSubjectToUser(Long userId, Long subjectId, int price, int level);

    int removeSubjectToUser(Long userId, Long subjectId, int level);

    List<SubjectInfo> getSubjectInfoListByUser(Long userId);

    Optional<Teaches> findByUserAndSubjectAndLevel(Long userId, Long subjectId, int level);
}
