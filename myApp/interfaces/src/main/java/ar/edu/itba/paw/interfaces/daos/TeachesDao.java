package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.TeacherInfo;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.SubjectInfo;
import ar.edu.itba.paw.models.Teaches;

import java.util.List;
import java.util.Optional;

public interface TeachesDao {

    List<Teaches> getByUser(Long userId);

    Teaches addSubjectToUser(Long userId, Long subjectId, int price, int level);

    int removeSubjectToUser(Long userId, Long subjectId, int level);

    Optional<Teaches> findByUserAndSubjectAndLevel(Long userId, Long subjectId, int level);

    List<Subject> getListOfAllSubjectsTeachedByUser(Long userId);

    /**
     * Get list of subjects being taught by teacher.
     *
     * @param teacherId The id of the teacher.
     * @return The list or empty list if no subjects are being taught.
     */
    List<Teaches> get(Long teacherId);

    /**
     * Retrieves the fee of the most expensive user
     * @param searchedSubject Subject to search for highest fee
     * @return Fee of most expensive user
     */
    Integer getMostExpensiveUserFee(String searchedSubject);

    List<TeacherInfo> filterUsers(String searchedSubject, Integer price, Integer minLevel, Integer maxLevel, Integer rate, Integer order, Integer offset);

    List<TeacherInfo> getTopRatedTeachers();

    List<TeacherInfo> getMostRequested();

//    List<Teaches> getSubjectInfoListByUser(Long teacherId);

    Optional<TeacherInfo> getTeacherInfo(Long teacherId);
}
