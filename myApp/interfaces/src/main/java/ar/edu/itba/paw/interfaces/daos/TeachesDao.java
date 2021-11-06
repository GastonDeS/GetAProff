package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Teaches;

import java.util.List;
import java.util.Optional;

public interface TeachesDao {

    Teaches addSubjectToUser(Long userId, Long subjectId, int price, int level);

    int removeSubjectToUser(Long userId, Long subjectId, int level);

    Optional<Teaches> findByUserAndSubjectAndLevel(Long userId, Long subjectId, int level);

    /**
     * Get list of subjects being taught by teacher.
     *
     * @param teacherId The id of the teacher.
     * @return The list or empty list if no subjects are being taught.
     */
    List<Teaches> get(Long teacherId);

    int getMaxPrice(Long teacherId);

    int getMinPrice(Long teacherId);

    /**
     * Retrieves the fee of the most expensive user
     * @param searchedSubject Subject to search for highest fee
     * @return Fee of most expensive user
     */
    Integer getMostExpensiveUserFee(String searchedSubject);

    List<Object> filterUsers(String searchedSubject, Integer price, Integer minLevel, Integer maxLevel, Integer rate, Integer order, Integer offset);

    List<Object> getTopRatedTeachers();

    List<Object> getHottest();
}
