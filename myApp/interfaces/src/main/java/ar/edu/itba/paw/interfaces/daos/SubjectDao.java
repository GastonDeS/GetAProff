package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectDao {
    Optional<Subject> findById(Long id);

    Subject create (String subject);

    List<Subject> listSubjects();

    Optional<Subject> findByName(String name);

    //TODO: CHEQUEAR SI USAMOS ESTO!
    List<Subject> getSubjectsMatching(String name);

    List<Subject> getHottestSubjects();
}
