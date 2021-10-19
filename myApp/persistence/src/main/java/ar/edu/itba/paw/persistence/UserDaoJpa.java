package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.utils.Pair;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class UserDaoJpa implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> get(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<CardProfile> filterUsers(String subject, Integer order, Integer price, Integer level, Integer rating, Integer offset) {
        return null;
    }

    @Override
    public List<CardProfile> getFavourites(Long uid) {
        return null;
    }

    @Override
    public Integer getPageQty(String subject, Integer price, Integer level, Integer rating) {
        return null;
    }

    @Override
    public int addFavourite(Long teacherId, Long studentId) {
        return 0;
    }

    @Override
    public int removeFavourite(Long teacherId, Long studentId) {
        return 0;
    }

    @Override
    public Boolean isFaved(Long teacherId, Long studentId) {
        return null;
    }

    @Override
    public int addRating(Long teacherId, Long studentId, float rate, String review) {
        return 0;
    }

    @Override
    public Pair<Float, Integer> getRatingById(Long teacherId) {
        return null;
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
        final TypedQuery<User> query = entityManager.createQuery("update User set schedule = :schedule where userid = :userid", User.class);
        query.setParameter("schedule", schedule);
        query.setParameter("userid", userId);
        return query.executeUpdate();
    }

    @Override
    public int setUserDescription(Long userId, String description) {
        return 0;
    }

    @Override
    public int setUserName(Long userId, String name) {
        return 0;
    }
}
