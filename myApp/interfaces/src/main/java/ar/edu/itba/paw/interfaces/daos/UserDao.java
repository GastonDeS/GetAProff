package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.utils.Pair;
import ar.edu.itba.paw.models.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDao {

    /**
     * Get a user.
     *
     * @param userId The id of the user.
     * @return The user.
     */
    Optional<User> get(Long userId);

    List<CardProfile> filterUsers(String subject, Integer order, Integer price, Integer level, Integer rating, Integer offset);

    List<User> getFavourites(Long uid);

    Integer getPageQty(String subject, Integer price, Integer level, Integer rating);

    int addFavourite(Long teacherId, Long studentId);

    int removeFavourite(Long teacherId, Long studentId);

    Boolean isFaved(Long teacherId, Long studentId);

    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @param mail The mail of the user.
     * @return The created user.
     */
    User create(String username, String mail, String password, String description, String schedule);

    /**
     * Find user by it's email address.
     *
     * @param mail The mail of the user.
     * @return User as Optional.
     */
    Optional<User> findByEmail(String mail);

    /**
     * Set user's schedule.
     *
     * @param userId The id of the user.
     * @param schedule The schedule of the user.
     * @return Rows updated i.e 1 if row updated and 0 if no update.
     */
    int setUserSchedule(Long userId, String schedule);

    /**
     * Set user's description.
     *
     * @param userId The id of the user.
     * @param description The description of the user.
     * @return Rows updated i.e 1 if row updated and 0 if no update.
     */
    int setUserDescription(Long userId, String description);

    /**
     * Set user's name.
     *
     * @param userId The id of the user.
     * @param name The name of the user.
     * @return Rows updated i.e 1 if row updated and 0 if no update.
     */
    int setUserName(Long userId, String name);
}
