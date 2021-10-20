package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ClassDao;
import ar.edu.itba.paw.interfaces.daos.SubjectDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

@Primary
@Repository
public class ClassDaoJpa implements ClassDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Class> get(Long id) {
        return Optional.ofNullable(em.find(Class.class, id));
    }

    @Override
    public List<Class> findClassesByStudentId(User student) {
        final TypedQuery<Class> query = em.createQuery("from Class c where c.student = :student", Class.class);
        query.setParameter("student", student);
        return query.getResultList();

    }

    @Override
    public List<Class> findClassesByTeacherId(User teacher) {
        final TypedQuery<Class> query = em.createQuery("from Class c where c.teacher = :teacher", Class.class);
        query.setParameter("teacher", teacher);
        return query.getResultList();
    }

    @Override
    public Class create(Long studentId, Long teacherId, int level, Long subjectId, int price, int status, String message) {
        final User student = em.getReference(User.class, studentId);
        final User teacher = em.getReference(User.class, teacherId);
        final Subject subject = em.getReference(Subject.class, subjectId);
        final Class newClass = new Class(student, teacher, subject, level, price, message);
        em.persist(newClass);
        return newClass;
    }

    @Override
    public int setStatus(Long classId, int status) {
        final Query query = em.createQuery("update Class set status = :status where classId = :classId");
        query.setParameter("status", status)
                .setParameter("classId", classId);
        return query.executeUpdate();
    }

    @Override
    public int setDeleted(Long classId, int deleted) {
        final Query query = em.createQuery("update Class set deleted = :deleted where classId = :classId");
        query.setParameter("deleted", deleted)
                .setParameter("classId", classId);
        return query.executeUpdate();
    }

    @Override
    public int setRequest(Long classId, String message) {
        final Query query = em.createQuery("update Class set messageRequest = :request where classId = :classId");
        query.setParameter("request", message)
                .setParameter("classId", classId);
        return query.executeUpdate();
    }

    @Override
    public int setReply(Long classId, String message) {
        final Query query = em.createQuery("update Class set messageReply = :reply where classId = :classId");
        query.setParameter("reply", message)
                .setParameter("classId", classId);
        return query.executeUpdate();
    }
}
