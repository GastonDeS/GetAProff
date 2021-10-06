package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectDao {
    Optional<Subject> findById(int id);

    Subject save(Subject subject);

    Subject create (String subject);

    Optional<List<Subject>> listSubjects();

    Optional<Subject> findByName(String name);

    Optional<List<Subject>> subjectsNotGiven(int userId);
}
