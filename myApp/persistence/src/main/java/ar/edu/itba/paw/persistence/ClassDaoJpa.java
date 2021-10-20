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
    private EntityManager entityManager;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SubjectDao subjectDao;

    @Override
    public Optional<Class> get(Long id) {
        return Optional.ofNullable(entityManager.find(Class.class, id));
    }

    @Override
    public List<Class> findClassesByStudentId(Long id) {
        Optional<User> student = userDao.get(id);
        if (student.isPresent()) {
            final TypedQuery<Class> query = entityManager.createQuery("from Class c where c.student = :student", Class.class);
            query.setParameter("student", student.get());
            return query.getResultList();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Class> findClassesByTeacherId(Long id) {
        Optional<User> teacher = userDao.get(id);
        if (teacher.isPresent()) {
            final TypedQuery<Class> query = entityManager.createQuery("from Class c where c.teacher = :teacher", Class.class);
            query.setParameter("teacher", teacher.get());
            return query.getResultList();
        }
        return new ArrayList<>();
    }

    @Override
    public Class create(Long studentId, Long teacherId, int level, Long subjectId, int price, int status, String message) {
        User student = userDao.get(studentId).orElseThrow(RuntimeException::new);
        User teacher = userDao.get(teacherId).orElseThrow(RuntimeException::new);
        Subject subject = subjectDao.findById(studentId).orElseThrow(RuntimeException::new);
        final Class newClass = new Class(student, teacher, subject, level, price, message);
        entityManager.persist(newClass);
        return newClass;
    }

    @Transactional
    @Override
    public int setStatus(Long classId, int status) {
        final Query query = entityManager.createQuery("update Class set status = :status where classId = :classId");
        query.setParameter("status", status)
                .setParameter("classId", classId);
        return query.executeUpdate();
    }

    @Transactional
    @Override
    public int setDeleted(Long classId, int deleted) {
        final Query query = entityManager.createQuery("update Class set deleted = :deleted where classId = :classId");
        query.setParameter("deleted", deleted)
                .setParameter("classId", classId);
        return query.executeUpdate();
    }

    @Transactional
    @Override
    public int setRequest(Long classId, String message) {
        final Query query = entityManager.createQuery("update Class set messageRequest = :request where classId = :classId");
        query.setParameter("request", message)
                .setParameter("classId", classId);
        return query.executeUpdate();
    }

    @Transactional
    @Override
    public int setReply(Long classId, String message) {
        final Query query = entityManager.createQuery("update Class set messageReply = :reply where classId = :classId");
        query.setParameter("reply", message)
                .setParameter("classId", classId);
        return query.executeUpdate();
    }
}
