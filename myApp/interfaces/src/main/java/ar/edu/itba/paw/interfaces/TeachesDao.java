package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Teaches;

import java.util.List;

public interface TeachesDao {
    List<Teaches> findUserBySubject(int subjectId);
    List<Teaches> findSubjectByUser(int userId);
    Teaches addSubjectToUser(int userid, int subjectid, int price, int level);
}
