package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.utils.Pair;
import ar.edu.itba.paw.models.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDao {
    Optional<User> get(int id);

    List<User> list();

    List<CardProfile> filterUsers(String subject, Integer order, Integer price, Integer level, Integer rating, Integer offset);

    List<CardProfile> getFavourites(int uid);

    Integer getPageQty(String subject, Integer price, Integer level, Integer rating);

    int addFavourite(int teacherId, int studentId);

    int removeFavourite(int teacherId, int studentId);

    Optional<String> isFaved(int teacherId, int studentId);

    int addRating(int teacherId, int studentId, float rate, String review);

    Pair<Float, Integer> getRatingById(int teacherId);

    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @param mail The mail of the user.
     * @return The created user.
     */
    User create(String username, String mail, String password, String description, String schedule);

    Optional<User> findByEmail(String mail);

    Map<Integer, List<String>> getUserSubjectsAndLevels(int userId);

    int setUserSchedule(int userId, String schedule);

    int setUserDescription(int userId, String description);
}
