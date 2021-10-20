package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.SubjectDao;
import ar.edu.itba.paw.models.Subject;
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
    EntityManager em;

    @Override
    public Optional<Subject> findById(Long id) {
        return Optional.ofNullable(em.find(Subject.class,id));
    }

    @Override
    public Subject create(String subject) {
        return null;
    }

    @Override
    public List<Subject> listSubjects() {
        TypedQuery<Subject> query = em.createQuery("SELECT s FROM Subject s",Subject.class);
        return query.getResultList();
    }

    @Override
    public Optional<Subject> findByName(String name) {
        return Optional.ofNullable(em.find(Subject.class,name));
    }
}
