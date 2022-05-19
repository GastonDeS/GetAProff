package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectService {

    Optional<Subject> findById(Long id);

    Subject create(String subject);

    List<Subject> list();

    List<Subject> getSubjectsMatching(String name);

    List<Subject> getHottestSubjects();
}
