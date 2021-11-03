package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ClassDao;
import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class ClassDaoJpa implements ClassDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Class> get(Long id) {
        return Optional.ofNullable(entityManager.find(Class.class, id));
    }

    @Override
    public List<Class> findClassesByStudentId(Long studentId) {
        final User student = entityManager.getReference(User.class, studentId);
        final TypedQuery<Class> query = entityManager.createQuery("from Class c where c.student = :student", Class.class);
        query.setParameter("student", student);
        return query.getResultList();
    }

    @Override
    public List<Class> findClassesByStudentAndStatus(Long studentId, Integer status) {
        final User student = entityManager.getReference(User.class, studentId);
        final TypedQuery<Class> query = entityManager.createQuery("from Class c where c.student = :student and c.status = :status", Class.class);
        query.setParameter("student", student).setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public List<Class> findClassesByStudentAndMultipleStatus(Long studentId, Integer status) {
        final User student = entityManager.getReference(User.class, studentId);
        final TypedQuery<Class> query = entityManager.createQuery("from Class c where c.student = :student and c.status >= :status", Class.class);
        query.setParameter("student", student).setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public List<Class> findClassesByTeacherId(Long teacherId) {
        final User teacher = entityManager.getReference(User.class, teacherId);
        final TypedQuery<Class> query = entityManager.createQuery("from Class c where c.teacher = :teacher", Class.class);
        query.setParameter("teacher", teacher);
        return query.getResultList();
    }

    @Override
    public List<Class> findClassesByTeacherAndStatus(Long teacherId, Integer status) {
        final User teacher = entityManager.getReference(User.class, teacherId);
        final TypedQuery<Class> query = entityManager.createQuery("from Class c where c.teacher = :teacher and c.status = :status", Class.class);
        query.setParameter("teacher", teacher).setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public List<Class> findClassesByTeacherAndMultipleStatus(Long teacherId, Integer status) {
        final User teacher = entityManager.getReference(User.class, teacherId);
        final TypedQuery<Class> query = entityManager.createQuery("from Class c where c.teacher = :teacher and c.status >= :status", Class.class);
        query.setParameter("teacher", teacher).setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public Class create(Long studentId, Long teacherId, int level, Long subjectId, int price) {
        final User student = entityManager.getReference(User.class, studentId);
        final User teacher = entityManager.getReference(User.class, teacherId);
        final Subject subject = entityManager.getReference(Subject.class, subjectId);
        final Class newClass = new Class(student, teacher, subject, level, price);
        entityManager.persist(newClass);
        return newClass;
    }

    @Override
    public int setStatus(Long classId, int status) {
        final Query query = entityManager.createQuery("update Class set status = :status where classId = :classId");
        query.setParameter("status", status)
                .setParameter("classId", classId);
        return query.executeUpdate();
    }
}
