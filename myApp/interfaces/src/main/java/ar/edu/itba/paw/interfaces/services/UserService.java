package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.TeacherInfo;
import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long userId);

    Page<TeacherInfo> getFavourites(Long userId, Integer page, Integer pageSize);

    int addFavourite(Long teacherId, Long studentId);

    int removeFavourite(Long teacherId, Long studentId);

    boolean isFaved(Long teacherId, Long studentId);

    Optional<User> create(String username, String mail, String password, String description, String schedule, Long roleid);

    Optional<User> findByEmail(String mail);

    Optional<User> getCurrentUser();

    int setUserSchedule(Long userId, String schedule);

    int setUserDescription(Long userId, String description);

    int setUserName(Long userId, String name);

    void setTeacherAuthorityToUser();
}
