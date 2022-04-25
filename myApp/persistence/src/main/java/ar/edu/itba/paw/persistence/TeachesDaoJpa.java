package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.TeachesDao;
import ar.edu.itba.paw.models.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class TeachesDaoJpa extends BasePaginationDaoImpl<TeacherInfo> implements TeachesDao {

    private static final Integer PAGE_SIZE = 9;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Teaches> getByUser(Long userId) {
        final User teacher = entityManager.getReference(User.class,userId);
        final TypedQuery<Teaches> query = entityManager.createQuery("FROM Teaches t where t.teacher = :teacher", Teaches.class);
        query.setParameter("teacher", teacher);
        return query.getResultList();
    }

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
    public List<Subject> getListOfAllSubjectsTeachedByUser(Long userId){
        final User teacher = entityManager.getReference(User.class,userId);
        final TypedQuery<Subject> query = entityManager.createQuery("SELECT DISTINCT (t.subject) FROM Teaches t where t.teacher = :teacher",Subject.class);
        query.setParameter("teacher", teacher);
        return query.getResultList();
    }

    @Override
    public List<Teaches> get(Long teacherId) {
        final User teacher = entityManager.getReference(User.class, teacherId);
        final TypedQuery<Teaches> query = entityManager.createQuery("from Teaches t where t.teacher = :teacher", Teaches.class);
        query.setParameter("teacher", teacher);
        return query.getResultList();
    }

    @Override
    public Integer getMostExpensiveUserFee(String searchedSubject) {
        final Query query = entityManager.createNativeQuery("select max(t.price) from Teaches t JOIN Subject s ON t.subjectid = s.subjectid  where LOWER(s.name) LIKE :name");
        query.setParameter("name", "%"+searchedSubject.toLowerCase()+"%");
        if (query.getSingleResult() == null) {
           return  ((Number) entityManager.createNativeQuery("select max(t.price) from Teaches t").getSingleResult()).intValue();
        }
        return ((Number) query.getSingleResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<TeacherInfo> filterUsers(String searchedSubject, Integer price, Integer minLevel, Integer maxLevel, Integer rate, Integer order, PageRequest pageRequest) {
        String queryStr = "select a3.teacherid as id, u.name as name, a3.maxPrice as maxPrice, a3.minPrice as minPrice, " +
                "coalesce(u.description, '') as desc, a3.rate as rate, coalesce(u.schedule, '') as sch, u.mail as mail, " +
                "a3.reviews as reviews from (select a2.teacherid as teacherid, max(a2.price) as maxPrice, min(a2.price) as minPrice, " +
                "coalesce(a1.rate, 0) as rate, coalesce(a1.reviews, 0) as reviews from (select t.userid as teacherid, " +
                "t.price as price, t.level as level from Teaches t JOIN Subject s on t.subjectid = s.subjectid " +
                "where lower(s.name) like '%'||:searchedSubject||'%' and t.price <= :price and " +
                "(t.level between :minLevel and :maxLevel or t.level = 0)) as a2 LEFT OUTER JOIN " +
                "(SELECT r.teacherid as teacherid, sum(coalesce(r.rate,0))/count(coalesce(r.rate,0)) as rate, count(r.rate) as reviews " +
                "FROM rating r group by teacherid) as a1 on a1.teacherid = a2.teacherid " +
                "group by a2.teacherid, rate, reviews) as a3 JOIN users u on a3.teacherid = u.userid where a3.rate >= :rate ";
        queryStr += checkOrdering(order);
        final Query query = entityManager.createNativeQuery(queryStr, "TeacherInfoMapping");
        query.setParameter("price", price)
                .setParameter("searchedSubject", "%"+searchedSubject.toLowerCase()+"%")
                .setParameter("minLevel", minLevel)
                .setParameter("maxLevel", maxLevel)
                .setParameter("rate", rate);
        return listBy(query, pageRequest);
    }

    private String checkOrdering(int order){
        String orderBy;
        switch (order) {
            case 1:
                orderBy= "ORDER BY a2.maxprice ASC ";
                break;
            case 2:
                orderBy= "ORDER BY a2.maxprice DESC ";
                break;
            case 3:
                orderBy= "ORDER BY a2.rate ASC ";
                break;
            case 4:
                orderBy= "ORDER BY a2.rate DESC ";
                break;
            default:
                orderBy = "";
        }
        return orderBy;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TeacherInfo> getTopRatedTeachers() {
        String queryStr = "select teacherid as id, u.name as name, maxPrice, minPrice, coalesce(u.description, '') as desc, " +
                "rate, coalesce(u.schedule, '') as sch, u.mail as mail, reviews from " +
                "(select t.userid as teacherid, max(t.price) as maxPrice, min(t.price) as minPrice, " +
                "coalesce(a1.rate, 0) as rate, coalesce(a1.reviews, 0) as reviews from Teaches t " +
                "LEFT OUTER JOIN (SELECT r.teacherid as teacherid, sum(coalesce(r.rate,0))/count(coalesce(r.rate,0)) as rate, " +
                "count(r.rate) as reviews FROM rating r group by teacherid) as a1 on t.userid = a1.teacherid " +
                "group by t.userid, a1.rate, a1.reviews) as a2 JOIN users u on a2.teacherid = u.userid order by rate desc";
        final Query query = entityManager.createNativeQuery(queryStr, "TeacherInfoMapping").setMaxResults(4);
        return (List<TeacherInfo>) query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TeacherInfo> getMostRequested() {
        String queryStr = "select a3.teacherid as id, a3.name as name, a3.maxPrice as maxPrice, a3.minPrice as minPrice, a3.description as desc,\n" +
                "a3.rate as rate, a3.schedule as sch, a3.mail as mail, a3.reviews as reviews " +
                "from (select a2.teacherid, u.name as name, a2.maxPrice as maxPrice, a2.minPrice as minPrice, " +
                "coalesce(u.description, '') as description, a2.rate as rate, coalesce(u.schedule, '') as schedule, u.mail as mail, " +
                "a2.reviews as reviews from (select t.userid as teacherid, max(t.price) as maxPrice, min(t.price) as minPrice, " +
                "COALESCE(a1.rate, 0) as rate, COALESCE(a1.reviews, 0) as reviews from Teaches t " +
                "LEFT OUTER JOIN (SELECT r.teacherid as teacherid, sum(coalesce(r.rate,0))/count(coalesce(r.rate,0)) as rate, count(r.rate) as reviews " +
                "FROM rating r group by teacherid) as a1 on a1.teacherid = t.userid group by t.userid, a1.rate, a1.reviews) " +
                "as a2 JOIN users u on a2.teacherid = u.userid) as a3 LEFT OUTER JOIN " +
                "classes c on a3.teacherid = c.teacherid group by a3.teacherid, a3.name, a3.maxPrice, " +
                "a3.minPrice, a3.description, a3.rate, a3.schedule, a3.mail, a3.reviews order by count(c.classid) desc";
        final Query query = entityManager.createNativeQuery(queryStr, "TeacherInfoMapping").setMaxResults(4);
        return (List<TeacherInfo>) query.getResultList();
    }

//    @Override
//    public List<Teaches> getSubjectInfoListByUser(Long teacherId) {
////        String queryStr = "select s.subjectid as id, s.name as name, t.price as price, t.level as level from teaches t join subject s on t.subjectid = s.subjectid where t.userid = :teacherId";
////        final Query query = entityManager.createNativeQuery(queryStr, "SubjectInfoMapping")
////                .setParameter("teacherId", teacherId);
//        final User teacher = entityManager.getReference(User.class, teacherId);
//        TypedQuery<Teaches> query = entityManager.createQuery("select t from Teaches t where t.teacher = :teacher", Teaches.class);
//        query.setParameter("teacher", teacher);
//        return query.getResultList();
//    }

    @SuppressWarnings("unchecked")
    @Override
    public Optional<TeacherInfo> getTeacherInfo(Long teacherId) {
        String queryStr = "select a2.teacherid as id, u.name as name, maxPrice, minPrice, coalesce(u.description, '') as desc, rate, " +
                "coalesce(u.schedule, '') as sch, u.mail as mail, reviews from " +
                "(select t.userid as teacherid, max(t.price) as maxPrice, min(t.price) as minPrice, " +
                "COALESCE(a1.rate, 0) as rate, COALESCE(a1.reviews, 0) as reviews " +
                "from Teaches t LEFT OUTER JOIN (SELECT r.teacherid as teacherid, " +
                "sum(coalesce(r.rate,0))/count(coalesce(r.rate,0)) as rate, count(r.rate) as reviews " +
                "FROM rating r group by teacherid) as a1 on a1.teacherid = t.userid where t.userid = :id " +
                "group by t.userid, a1.rate, a1.reviews) as a2 JOIN users u on a2.teacherid = u.userid";
        final Query query = entityManager.createNativeQuery(queryStr, "TeacherInfoMapping")
                .setParameter("id", teacherId);
        List<TeacherInfo> teacherInfos = (List<TeacherInfo>) query.getResultList();
        return teacherInfos.isEmpty() ? Optional.empty() : Optional.of(teacherInfos.get(0));
    }
}
