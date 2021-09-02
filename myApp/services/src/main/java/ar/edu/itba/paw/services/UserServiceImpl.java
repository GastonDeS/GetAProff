package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public User findById(int id) {
        return this.userDao.get(id);
    }
    public List<User> list() {
        return this.userDao.list();
    }

    @Override
    public User create(String username) {
        return userDao.create(username);
    }
}
