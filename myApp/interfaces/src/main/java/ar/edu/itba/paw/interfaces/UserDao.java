package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;

import java.util.List;

public interface UserDao {
    User get(int id);
    List<User> list();
    User save(User user);

    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @param mail The mail of the user.
     * @return The created user.
     */
    User create(String username, String mail);
}
