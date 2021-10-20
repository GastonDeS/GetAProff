package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.SubjectDao;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class SubjectDaoJpa implements SubjectDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Subject> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Subject.class, id));
    }

    @Override
    public Subject create(String subject) {
        final Subject newSubject = new Subject(subject, null);
        entityManager.persist(newSubject);
        return newSubject;
    }

    @Override
    public List<Subject> listSubjects() {
        final TypedQuery<Subject> query = entityManager.createQuery("from Subject", Subject.class);
        return query.getResultList();
    }

    @Override
    public Optional<Subject> findByName(String name) {
        final TypedQuery<Subject> query = entityManager.createQuery("from Subject s where s.name = :name", Subject.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst();
    }
}
