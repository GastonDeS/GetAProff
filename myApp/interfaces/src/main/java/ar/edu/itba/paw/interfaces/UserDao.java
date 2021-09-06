package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.User;
import java.util.List;

public interface UserDao {
    User get(int id);
    List<User> list();
    User save(User user);
    List<CardProfile> findUsersBySubject(int subjectId);

    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @return The created user.
     */

    User create(String username);
}
