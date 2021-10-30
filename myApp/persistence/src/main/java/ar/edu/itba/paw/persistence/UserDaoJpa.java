package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.UserDao;
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
public class UserDaoJpa implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> get(Long userId) {
        return Optional.ofNullable(entityManager.find(User.class, userId));
    }

    @Override
    public List<User> getFavourites(Long userId) {
        return entityManager.getReference(User.class, userId).getFavourites();
    }

    @Override
    public int addFavourite(Long teacherId, Long studentId) {
        User student = entityManager.find(User.class, studentId);
        User teacher = entityManager.find(User.class, teacherId);
        if (student != null && teacher != null) {
            student.getFavourites().add(teacher);
            return 1;
        }
        return 0;
    }

    @Override
    public int removeFavourite(Long teacherId, Long studentId) {
        User student = entityManager.find(User.class, studentId);
        User teacher = entityManager.find(User.class, teacherId);
        if (student != null && teacher != null) {
            student.getFavourites().remove(teacher);
            return 1;
        }
        return 0;
    }

    @Override
    public Boolean isFaved(Long teacherId, Long studentId) {
        User student = entityManager.find(User.class, studentId);
        if (student != null) {
            for (User teacher : student.getFavourites()) {
                if (teacher.getId().equals(teacherId)) {
                    return true;
                }
            }
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
