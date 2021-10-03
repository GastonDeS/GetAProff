package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectDao {
    Subject findById(int id);

    Subject save(Subject subject);

    Subject create (String subject);

    List<Subject> listSubjects();

    Optional<Subject> findByName(String name);

    List<Subject> subjectsNotGiven(int userId);
}
