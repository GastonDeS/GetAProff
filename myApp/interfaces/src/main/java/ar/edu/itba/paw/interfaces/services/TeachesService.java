package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.SubjectInfo;
import ar.edu.itba.paw.models.Teaches;

import java.util.List;
import java.util.Optional;

public interface TeachesService {
    Optional<Teaches> addSubjectToUser(Long userId, Long subjectId, int price, int level);

    int removeSubjectToUser(Long userId, Long subjectId, int level);

    List<SubjectInfo> getSubjectInfoListByUser(Long teacherId);

    Optional<Teaches> findByUserAndSubjectAndLevel(Long userId, Long subjectId, int level);

    int getMaxPrice(Long teacherId);

    int getMinPrice(Long teacherId);

    List<Teaches> get(Long teacherId);

    List<Teaches> findUsersTeaching(Subject s);

    List<CardProfile> filterTeachingUsers(String subject);
}
