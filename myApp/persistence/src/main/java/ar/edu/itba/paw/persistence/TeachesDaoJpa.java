package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.TeachesDao;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.Teaches;
import ar.edu.itba.paw.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class TeachesDaoJpa implements TeachesDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Teaches addSubjectToUser(Long userId, Long subjectId, int price, int level) {
        final User teacher = entityManager.getReference(User.class, userId);
        final Subject subject = entityManager.getReference(Subject.class, subjectId);
        final Teaches teaches = new Teaches(teacher, subject, price, level);
        entityManager.persist(teaches);
        return teaches;
    }

    @Override
    public int removeSubjectToUser(Long userId, Long subjectId, int level) {
        final User teacher = entityManager.getReference(User.class, userId);
        final Subject subject = entityManager.getReference(Subject.class, subjectId);
        final Query query = entityManager.createQuery("delete from Teaches t where t.subject = :subject and t.teacher = :teacher and t.level = :level");
        query.setParameter("subject", subject)
                .setParameter("teacher", teacher)
                .setParameter("level", level);
        return query.executeUpdate();
    }

    @Override
    public Optional<Teaches> findByUserAndSubjectAndLevel(Long userId, Long subjectId, int level) {
        final User teacher = entityManager.getReference(User.class, userId);
        final Subject subject = entityManager.getReference(Subject.class, subjectId);
        final TypedQuery<Teaches> query = entityManager.createQuery("from Teaches t where t.subject = :subject and t.teacher = :teacher and t.level = :level", Teaches.class);
        query.setParameter("subject", subject)
                .setParameter("teacher", teacher)
                .setParameter("level", level);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<Teaches> get(Long teacherId) {
        final User teacher = entityManager.getReference(User.class, teacherId);
        final TypedQuery<Teaches> query = entityManager.createQuery("from Teaches t where t.teacher = :teacher", Teaches.class);
        query.setParameter("teacher", teacher);
        return query.getResultList();
    }

    @Override
    public int getMaxPrice(Long teacherId) {
        final Query query = entityManager.createNativeQuery("select max(t.price) from Teaches t where t.userid = :userid group by t.userid");
        query.setParameter("userid", teacherId);
        return ((Number) query.getSingleResult()).intValue();
    }

    @Override
    public int getMinPrice(Long teacherId) {
        final Query query = entityManager.createNativeQuery("select min(t.price) from Teaches t where t.userid = :userid group by t.userid");
        query.setParameter("userid", teacherId);
        return ((Number) query.getSingleResult()).intValue();
    }

    @Override
    public List<Teaches> filterTeachersTeachingSubject(String searchedSubject) {
        final TypedQuery<Teaches> query = entityManager.createQuery("select distinct (t) from Subject s inner join s.teachersTeachingSubject t where LOWER(s.name) LIKE :name", Teaches.class);
        query.setParameter("name", "%"+searchedSubject.toLowerCase()+"%");
        return query.getResultList();
    }


}
