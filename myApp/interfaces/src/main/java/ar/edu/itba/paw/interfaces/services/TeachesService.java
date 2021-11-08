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

    List<Subject> getListOfAllSubjectsTeachedByUser(Long userId);

    Optional<Teaches> findByUserAndSubjectAndLevel(Long userId, Long subjectId, int level);

    int getMaxPrice(Long teacherId);

    int getMinPrice(Long teacherId);

    List<Teaches> get(Long teacherId);

    List<CardProfile> findTeachersTeachingSubject(String searchedSubject, String offset);

    List<CardProfile> filterUsers(String searchedSubject, String order, String price, String level, String rating, String offset);

    Integer getMostExpensiveUserFee(String searchedSubject);

    Integer getPageQty(String searchedSubject, String price, String level, String rating);

    Integer getPageQty(String searchedSubject);

    List<CardProfile> getTopRatedTeachers();

    List<CardProfile> getMostRequested();

}
