package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ClassDao;
import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.ClassInfo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Primary
@Repository
public class ClassDaoJpa implements ClassDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Class> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ClassInfo> findClassesByStudentId(Long id) {
        final TypedQuery<ClassInfo> query = em.createQuery("from Class class where class.id = :id",ClassInfo.class);
        query.setParameter("id",id);
        return new ArrayList<>(query.getResultList());
    }

//    @Override
//    public List<ClassInfo> findClassesByTeacherId(Long id) {
//        String query = "SELECT classId,  st.name AS studentName, t.name AS teacherName, level, status,\n" +
//                "       price, s.name AS subjectName, a1.request AS request, a1.reply AS reply, a1.deleted AS deleted\n" +
//                "FROM (SELECT * FROM classes WHERE teacherid = ?) AS a1 JOIN users st ON st.userId = a1.studentId\n" +
//                "             JOIN users t ON t.userid = a1.teacherid JOIN subject s on a1.subjectid = s.subjectid";
//        List<ClassInfo> list = jdbcTemplate.query(query, new Object[]{id}, CLASS_INFO_ROW_MAPPER);
//        return list.isEmpty() ? new ArrayList<>() : list;
//    }

    @Override
    public List<ClassInfo> findClassesByTeacherId(Long id) {
        final TypedQuery<ClassInfo> query = em.createQuery("from ClassInfo class where class.teacherId = :id",ClassInfo.class);
        query.setParameter("id",id);
        return new ArrayList<>(query.getResultList());
    }

    @Override
    public Class create(Long studentId, Long teacherId, int level, Long subjectId, int price, int status, String message) {
        final Class newClass =  new Class(null,studentId,teacherId,level,subjectId,price,status,message,"",0);
        em.persist(newClass);
        return newClass;
    }

    @Override
    public int setStatus(Long classId, int status) {
        final TypedQuery<Class> query = em.createQuery("update Class set status = :status where classId = :classId",Class.class);
        query.setParameter("status",status)
                .setParameter("classId",classId);
        return query.executeUpdate();
    }

    @Override
    public int setDeleted(Long classId, int deleted) {
        final TypedQuery<Class> query = em.createQuery("update Class set deleted = :deleted where classId = :classId",Class.class);
        query.setParameter("deleted",deleted)
                .setParameter("classId",classId);
        return query.executeUpdate();
    }

    @Override
    public int setRequest(Long classId, String message) {
        final TypedQuery<Class> query = em.createQuery("update Class set request = :request where classId = :classId",Class.class);
        query.setParameter("request",message)
                .setParameter("classId",classId);
        return query.executeUpdate();
    }

    @Override
    public int setReply(Long classId, String message) {
        final TypedQuery<Class> query = em.createQuery("update Class set reply = :reply where classId = :classId",Class.class);
        query.setParameter("reply",message)
                .setParameter("classId",classId);
        return query.executeUpdate();
    }
}
