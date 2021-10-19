package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.utils.Pair;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);

    List<CardProfile> filterUsers(String subject, String order, String price, String level, String rating, String offset);

    List<CardProfile> filterUsers(String subject);

    List<CardProfile> filterUsers(String subject, String offset);

    Integer getPageQty(String subject, String price, String level, String rating);

    Integer getPageQty(String subject);

    List<CardProfile> getFavourites(Long uid);

    int addFavourite(Long teacherId, Long studentId);

    int removeFavourite(Long teacherId, Long studentId);

    boolean isFaved(Long teacherId, Long studentId);

    int addRating(Long teacherId, Long studentId, float rate, String review);

    Pair<Float, Integer> getRatingById(Long teacherId);

    /**
     * Retrieves the fee of the most expensive user
     * @param subject Subject to search for highest fee
     * @return Fee of most expensive user
     */
    Integer mostExpensiveUserFee(String subject);

    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @param mail The mail of the user.
     * @return The created user.
     */
    Optional<User> create(String username, String mail, String password, String description, String schedule, Long userole);

    Optional<User> findByEmail(String mail);

    Optional<User> getCurrentUser();

    int setUserSchedule(Long userId, String schedule);

    int setUserDescription(Long userId, String description);

    int setUserName(Long userId, String name);
}
