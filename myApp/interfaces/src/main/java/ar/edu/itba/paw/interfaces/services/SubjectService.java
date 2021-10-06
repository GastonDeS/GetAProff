package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectService {

    Optional<Subject> findById(int id);

    Subject create(String subject);

    List<Subject> list();

}
