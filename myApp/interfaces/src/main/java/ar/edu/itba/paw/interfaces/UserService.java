package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.User;

import java.util.List;

public interface UserService {
    User findById(String id);
    List<User> list();
}
