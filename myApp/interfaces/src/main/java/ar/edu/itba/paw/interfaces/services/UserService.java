package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.utils.Pair;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findById(int id);

    Optional<List<CardProfile>> filterUsers(String subject, String price, String level);

    Optional<List<CardProfile>> filterUsers(String subject);

    Optional<List<CardProfile>> getFavourites(int uid);

    int addFavourite(int teacherId, int studentId);

    int removeFavourite(int teacherId, int studentId);

    boolean isFaved(int teacherId, int studentId);

    int addRating(int teacherId, int studentId, float rate, String review);

    Pair<Float, Integer> getRatingById(int teacherId);

    /**
         * Retrieves user schedule
         *
         * @param userId The id of the user.
         * @return A String description of the user's schedule.
         */
    String getUserSchedule(int userId);

    /**
     * Retrieves all the users registered
     *
     * @return A list of User
     */
    List<User> list();

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
    User create(String username, String mail, String password, String description, String schedule, int userole);

    Optional<User> findByEmail(String mail);

    User getCurrentUser();

    String getUserDescription(int userId);

    int setUserSchedule(int userId, String schedule);

    int setUserDescription(int userId, String description);
}
