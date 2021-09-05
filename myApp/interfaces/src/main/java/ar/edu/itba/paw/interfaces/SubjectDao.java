package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Subject;

public interface SubjectDao {
    Subject findById(int id);
}
