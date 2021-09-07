package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface UserService {
    User findById(int id);
    List<CardProfile> findUsersBySubject(int subjectId);
    List<User> list();
    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @param mail The mail of the user.
     * @return The created user.
     */
    User create(String username, String mail);
}
