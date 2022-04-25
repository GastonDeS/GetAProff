package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.LectureDao;
import ar.edu.itba.paw.models.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Repository
public class LectureDaoJpa extends BasePaginationDaoImpl<Lecture> implements LectureDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Lecture> get(Long id) {
        return Optional.ofNullable(entityManager.find(Lecture.class, id));
    }


    @Override
    public Page<Lecture> findClassesByStudentId(Long studentId, PageRequest pageRequest) {
        final User student = entityManager.getReference(User.class, studentId);
        final TypedQuery<Lecture> query = entityManager.createQuery("from Lecture c where c.student = :student", Lecture.class);
        query.setParameter("student", student);
        return listBy(query, pageRequest);
    }

    @Override
    public Page<Lecture> findClassesByStudentAndStatus(Long studentId, Integer status, PageRequest pageRequest) {
        final User student = entityManager.getReference(User.class, studentId);
        final TypedQuery<Lecture> query = entityManager.createQuery("from Lecture c where c.student = :student and c.status = :status", Lecture.class);
        query.setParameter("student", student).setParameter("status", status);
        return listBy(query, pageRequest);
    }

    @Override
    public Page<Lecture> findClassesByStudentAndMultipleStatus(Long studentId, Integer status, PageRequest pageRequest) {
        final User student = entityManager.getReference(User.class, studentId);
        final TypedQuery<Lecture> query = entityManager.createQuery("from Lecture c where c.student = :student and c.status >= :status", Lecture.class);
        query.setParameter("student", student).setParameter("status", status);
        return listBy(query, pageRequest);
    }

    @Override
    public Page<Lecture> findClassesByTeacherId(Long teacherId, PageRequest pageRequest) {
        final User teacher = entityManager.getReference(User.class, teacherId);
        final TypedQuery<Lecture> query = entityManager.createQuery("from Lecture c where c.teacher = :teacher", Lecture.class);
        query.setParameter("teacher", teacher);
        return listBy(query, pageRequest);
    }

    @Override
    public Page<Lecture> findClassesByTeacherAndStatus(Long teacherId, Integer status, PageRequest pageRequest) {
        final User teacher = entityManager.getReference(User.class, teacherId);
        final TypedQuery<Lecture> query = entityManager.createQuery("from Lecture c where c.teacher = :teacher and c.status = :status", Lecture.class);
        query.setParameter("teacher", teacher).setParameter("status", status);
        return listBy(query, pageRequest);
    }

    @Override
    public Page<Lecture> findClassesByTeacherAndMultipleStatus(Long teacherId, Integer status, PageRequest pageRequest) {
        final User teacher = entityManager.getReference(User.class, teacherId);
        final TypedQuery<Lecture> query = entityManager.createQuery("from Lecture c where c.teacher = :teacher and c.status >= :status", Lecture.class);
        query.setParameter("teacher", teacher).setParameter("status", status);
        return listBy(query, pageRequest);
    }

    @Override
    public Lecture create(Long studentId, Long teacherId, int level, Long subjectId, int price) {
        final User student = entityManager.getReference(User.class, studentId);
        final User teacher = entityManager.getReference(User.class, teacherId);
        final Subject subject = entityManager.getReference(Subject.class, subjectId);
        final Lecture newLecture = new Lecture(student, teacher, subject, level, price,
                Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
        entityManager.persist(newLecture);
        return newLecture;
    }

    @Override
    public int setStatus(Long classId, int status) {
        final Query query = entityManager.createQuery("update Lecture set status = :status where classId = :classId");
        query.setParameter("status", status)
                .setParameter("classId", classId);
        return query.executeUpdate();
    }

    @Override
    public Integer getNotificationsCount(Long classId, int role) {
        final TypedQuery<Post> query;
        if (role == 0) {
            query = entityManager.createQuery(
                    "from Post p\n" +
                            "    where p.time > p.associatedLecture.teacherLastTime\n" +
                            "              AND p.associatedLecture.classId = :classId", Post.class);
        } else {
            query = entityManager.createQuery(
                    "from Post p\n" +
                            "    where p.time > p.associatedLecture.studentLastTime\n" +
                            "              AND p.associatedLecture.classId = :classId", Post.class);
        }
        query.setParameter("classId",classId);
        return query.getResultList().size();
    }

    @Override
    public int refreshTime(Long classId, int role) {
        final Query query;
        if (role == 0) {
            query = entityManager.createQuery("update Lecture set teacherLastTime = CURRENT_TIMESTAMP where classId = :classId");
        } else {
            query = entityManager.createQuery("update Lecture set studentLastTime = CURRENT_TIMESTAMP where classId = :classId");
        }
        query.setParameter("classId", classId);
        return query.executeUpdate();
    }

    @Override
    public int addSharedFileToLecture(Long subjectFileId, Long lectureId) {
        final SubjectFile subjectFile = entityManager.find(SubjectFile.class,subjectFileId);
        final Lecture currentLecture = entityManager.find(Lecture.class, lectureId);
        if(currentLecture.getSharedFilesByTeacher().contains(subjectFile))
            return 0;
        currentLecture.getSharedFilesByTeacher().add(subjectFile);
        return 1;
    }

    @Override
    public int stopSharingFileInLecture(Long subjectFileId, Long lectureId) {
        final SubjectFile subjectFile = entityManager.find(SubjectFile.class,subjectFileId);
        final Lecture currentLecture = entityManager.find(Lecture.class, lectureId);
        if(!currentLecture.getSharedFilesByTeacher().contains(subjectFile))
            return 0;
        currentLecture.getSharedFilesByTeacher().remove(subjectFile);
        return 1;
    }

    @Override
    public List<SubjectFile> getSharedFilesByTeacher(Long lectureId) {
        final Lecture currentLecture = entityManager.getReference(Lecture.class, lectureId);
        return currentLecture.getSharedFilesByTeacher();
    }
}
