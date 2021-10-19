package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.utils.Pair;
import ar.edu.itba.paw.models.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDao {
    Optional<User> get(Long id);

    List<CardProfile> filterUsers(String subject, Integer order, Integer price, Integer level, Integer rating, Integer offset);

    List<CardProfile> getFavourites(Long uid);

    Integer getPageQty(String subject, Integer price, Integer level, Integer rating);

    int addFavourite(Long teacherId, Long studentId);

    int removeFavourite(Long teacherId, Long studentId);

    Boolean isFaved(Long teacherId, Long studentId);

    int addRating(Long teacherId, Long studentId, float rate, String review);

    Pair<Float, Integer> getRatingById(Long teacherId);

    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @param mail The mail of the user.
     * @return The created user.
     */
    User create(String username, String mail, String password, String description, String schedule);

    Optional<User> findByEmail(String mail);

    int setUserSchedule(Long userId, String schedule);

    int setUserDescription(Long userId, String description);

    int setUserName(Long userId, String name);
}
