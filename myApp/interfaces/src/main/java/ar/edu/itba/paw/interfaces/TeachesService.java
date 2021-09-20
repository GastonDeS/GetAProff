package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Teaches;

public interface TeachesService {
    Teaches addSubjectToUser(int userid, int subjectid, int price, int level);
}
