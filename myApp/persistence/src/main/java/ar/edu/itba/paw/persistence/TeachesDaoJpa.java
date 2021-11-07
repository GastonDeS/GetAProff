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
public class TeachesDaoJpa implements TeachesDao {

    private static final Integer PAGE_SIZE = 9;

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
    public List<Object> filterUsers(String searchedSubject, Integer price, Integer minLevel, Integer maxLevel, Integer rate, Integer order, Integer offset) {
        String queryStr = "select teacherid, u.name, maxPrice, minPrice, coalesce(u.description, ''), rate " +
                "from (select a1.teacherid as teacherid, max(a1.price) as maxPrice, min(a1.price) as minPrice, " +
                "sum(coalesce(r.rate,0))/count(coalesce(r.rate,0)) as rate " +
                "from (select t.userid as teacherid, t.price as price, t.level as level from Teaches t JOIN Subject s " +
                "on t.subjectid = s.subjectid where lower(s.name) like '%'||:searchedSubject||'%' and t.price <= :price and " +
                "(t.level between :minLevel and :maxLevel or t.level = 0)) as a1 LEFT OUTER JOIN Rating r on a1.teacherid = r.teacherid " +
                "group by a1.teacherid) as a2 JOIN users u on a2.teacherid = u.userid where a2.rate >= :rate ";
        queryStr += checkOrdering(order);
        final Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter("price", price)
                .setParameter("searchedSubject", "%"+searchedSubject.toLowerCase()+"%")
                .setParameter("minLevel", minLevel)
                .setParameter("maxLevel", maxLevel)
                .setParameter("rate", rate);
        if (offset > 0) {
            query.setFirstResult((offset - 1) * PAGE_SIZE)
                    .setMaxResults(PAGE_SIZE);
        }
        System.out.println("SIZEEEE" + query.getResultList().size());
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object> getTopRatedTeachers() {
        String queryStr = "select teacherid, u.name, maxPrice, minPrice, coalesce(u.description, ''), rate " +
                "from (select t.userid as teacherid, max(t.price) as maxPrice, min(t.price) as minPrice, " +
                "sum(coalesce(r.rate,0))/count(coalesce(r.rate,0)) as rate " +
                "from Teaches t LEFT OUTER JOIN Rating r on t.userid = r.teacherid " +
                "group by t.userid) as a2 JOIN users u on a2.teacherid = u.userid order by rate desc";
        final Query query = entityManager.createNativeQuery(queryStr).setMaxResults(4);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object> getMostRequested() {
        String queryStr = "select a3.teacherid, a3.name, a3.maxPrice, a3.minPrice, a3.description, " +
                "a3.rate from (select a2.teacherid, u.name as name, a2.maxPrice as maxPrice, a2.minPrice as minPrice, " +
                "coalesce(u.description, '') as description, a2.rate as rate from " +
                "(select t.userid as teacherid, max(t.price) as maxPrice, min(t.price) as minPrice, " +
                "sum(coalesce(r.rate,0))/count(coalesce(r.rate,0)) as rate " +
                "from Teaches t LEFT OUTER JOIN Rating r on t.userid = r.teacherid " +
                "group by t.userid) as a2 JOIN users u on a2.teacherid = u.userid) as a3 LEFT OUTER JOIN " +
                "classes c on a3.teacherid = c.teacherid group by a3.teacherid, a3.name, a3.maxPrice," +
                "a3.minPrice, a3.description, a3.rate order by count(c.classid) desc";
        final Query query = entityManager.createNativeQuery(queryStr).setMaxResults(4);
        return query.getResultList();
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
}
