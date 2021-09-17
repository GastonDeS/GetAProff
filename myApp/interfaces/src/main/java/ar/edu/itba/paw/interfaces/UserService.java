package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findById(int id);
    List<CardProfile> findUsersBySubjectId(int subjectId);
    List<CardProfile> findUsersBySubject(String subject);
    List<User> list();
    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @param mail The mail of the user.
     * @return The created user.
     */
    User create(String username, String mail, String password, int userRole);
    Optional<User> findByEmail(String mail);
}
