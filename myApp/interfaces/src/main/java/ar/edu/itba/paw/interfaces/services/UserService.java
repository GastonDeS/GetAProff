package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long userId);

    List<CardProfile> getFavourites(Long userId);

    int addFavourite(Long teacherId, Long studentId);

    int removeFavourite(Long teacherId, Long studentId);

    boolean isFaved(Long teacherId, Long studentId);

    Optional<User> create(String username, String mail, String password, String description, String schedule, Long userole);

    Optional<User> findByEmail(String mail);

    Optional<User> getCurrentUser();

    int setUserSchedule(Long userId, String schedule);

    int setUserDescription(Long userId, String description);

    int setUserName(Long userId, String name);

    void setTeacherAuthorityToUser();
}
