package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findById(int id);
    List<CardProfile> findUsersBySubjectId(int subjectId);
    List<CardProfile> filterUsers(String subject, String price, String level);
    List<CardProfile> filterUsers(String subject);
    List<User> list();
    Integer mostExpensiveUserFee(String subject);

    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @param mail The mail of the user.
     * @return The created user.
     */
    Optional<User> create(String username, String mail, String password, int userRole);
    Optional<User> findByEmail(String mail);
}
