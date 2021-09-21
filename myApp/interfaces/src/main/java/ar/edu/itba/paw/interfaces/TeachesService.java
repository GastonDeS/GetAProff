package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Teaches;

import java.util.List;

public interface TeachesService {
    Teaches addSubjectToUser(int userid, int subjectid, int price, int level);

    List<Teaches> getSubjectListByUser(int userid);
}
