package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.TeacherInfo;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoJpa implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> get(Long userId) {
        return Optional.ofNullable(entityManager.find(User.class, userId));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TeacherInfo> getFavourites(Long userId) {
        final String queryStr = "select a2.teacherid as id, a2.name as name, a2.maxPrice as maxPrice, a2.minPrice as minPrice, a2.description as desc, " +
                "sum(coalesce(r.rate,0))/count(coalesce(r.rate,0)) as rate, a2.schedule as sch, a2.mail as mail " +
                "from (select a1.teacherid as teacherid, a1.name as name, max(t.price) as maxPrice, " +
                "min(t.price) as minPrice, a1.description as description, a1.schedule as schedule, a1.mail as mail " +
                "from (select u.userid as teacherid, u.name as name, coalesce(u.description, '') as description, " +
                "coalesce(u.schedule, '') as schedule, u.mail as mail from users u JOIN favourites f on u.userid = f.teacherid where f.studentid = :userId) " +
                "as a1 join teaches t on a1.teacherid = t.userid group by a1.teacherid, a1.name, a1.description, a1.schedule, a1.mail) " +
                "as a2 left outer join rating r on r.teacherid = a2.teacherid group by a2.teacherid, a2.name, a2.maxPrice, a2.minPrice, a2.description, a2.schedule, a2.mail";
        final Query query = entityManager.createNativeQuery(queryStr, "TeacherInfoMapping");
        query.setParameter("userId", userId);
        return (List<TeacherInfo>) query.getResultList();
    }

    @Override
    public int addFavourite(Long teacherId, Long studentId) {
        final User student = entityManager.find(User.class, studentId);
        final User teacher = entityManager.find(User.class, teacherId);
        if (student != null && teacher != null) {
            if (student.getFavourites().contains(teacher)) return 0;
            student.getFavourites().add(teacher);
            return 1;
        }
        return 0;
    }

    @Override
    public int removeFavourite(Long teacherId, Long studentId) {
        final User student = entityManager.find(User.class, studentId);
        final User teacher = entityManager.find(User.class, teacherId);
        if (student != null && teacher != null) {
            if (!student.getFavourites().contains(teacher)) return 0;
            student.getFavourites().remove(teacher);
            return 1;
        }
        return 0;
    }

    @Override
    public Boolean isFaved(Long teacherId, Long studentId) {
        final User student = entityManager.find(User.class, studentId);
        final User teacher = entityManager.find(User.class, teacherId);
        if (student != null) {
            return student.getFavourites().contains(teacher);
        }
        return false;
    }

    @Override
    public User create(String username, String mail, String password, String description, String schedule) {
        final User user = new User(username, password, null, mail, description, schedule);
        entityManager.persist(user);
        return user;
    }

    @Override
    public Optional<User> findByEmail(String mail) {
        final TypedQuery<User> query = entityManager.createQuery("from User u where u.mail = :mail", User.class);
        query.setParameter("mail", mail);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public int setUserSchedule(Long userId, String schedule) {
        final Query query = entityManager.createQuery("update User set schedule = :schedule where userid = :userid");
        query.setParameter("schedule", schedule);
        query.setParameter("userid", userId);
        return query.executeUpdate();
    }

    @Override
    public int setUserDescription(Long userId, String description) {
        final Query query = entityManager.createQuery("update User set description = :description where userid = :userid");
        query.setParameter("description", description);
        query.setParameter("userid", userId);
        return query.executeUpdate();
    }

    @Override
    public int setUserName(Long userId, String name) {
        final Query query = entityManager.createQuery("update User set name = :name where userid = :userid");
        query.setParameter("name", name);
        query.setParameter("userid", userId);
        return query.executeUpdate();
    }
}
