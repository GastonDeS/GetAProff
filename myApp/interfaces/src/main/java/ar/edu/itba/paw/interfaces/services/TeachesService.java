package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.TeacherInfo;
import ar.edu.itba.paw.models.Teaches;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TeachesService {
    Optional<Teaches> addSubjectToUser(Long userId, Long subjectId, int price, int level);

    int removeSubjectToUser(Long userId, Long subjectId, int level);

//    List<Teaches> getSubjectInfoListByUser(Long teacherId);

    List<Subject> getListOfAllSubjectsTaughtByUser(Long userId);

    Optional<Teaches> findByUserAndSubjectAndLevel(Long userId, Long subjectId, int level);

    List<Teaches> get(Long teacherId);

//    Page<TeacherInfo> findTeachersTeachingSubject(String searchedSubject, Integer page, Integer pageSize);

    Page<TeacherInfo> filterUsers(String searchedSubject, Integer order, Integer price, Integer level, Integer rating, Integer page, Integer pageSize);

    Integer getMostExpensiveUserFee(String searchedSubject);

//    Integer getPageQty(String searchedSubject, Integer price, Integer level, Integer rating);

//    Integer getPageQty(String searchedSubject);

    List<TeacherInfo> getTopRatedTeachers();

    List<TeacherInfo> getMostRequested();

    Map<Subject, List<Integer>> getSubjectAndLevelsTaughtByUser(Long userId);

    Optional<TeacherInfo> getTeacherInfo(Long teacherId);

    Map<Subject, List<Integer>> getSubjectAndLevelsAvailableForUser(Long userId);
}
