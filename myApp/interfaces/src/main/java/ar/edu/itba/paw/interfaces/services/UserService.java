package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long userId);

    List<CardProfile> filterUsers(String subject, String order, String price, String level, String rating, String offset);

    List<CardProfile> filterUsers(String subject);

    List<CardProfile> filterUsers(String subject, String offset);

    Integer getPageQty(String subject, String price, String level, String rating);

    Integer getPageQty(String subject);

    List<CardProfile> getFavourites(Long userId);

    int addFavourite(Long teacherId, Long studentId);

    int removeFavourite(Long teacherId, Long studentId);

    boolean isFaved(Long teacherId, Long studentId);

    /**
     * Retrieves the fee of the most expensive user
     * @param subject Subject to search for highest fee
     * @return Fee of most expensive user
     */
    Integer mostExpensiveUserFee(String subject);

    Optional<User> create(String username, String mail, String password, String description, String schedule, Long userole);

    Optional<User> findByEmail(String mail);

    Optional<User> getCurrentUser();

    int setUserSchedule(Long userId, String schedule);

    int setUserDescription(Long userId, String description);

    int setUserName(Long userId, String name);
}
