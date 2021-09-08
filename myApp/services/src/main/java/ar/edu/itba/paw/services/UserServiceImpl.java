package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.CardProfile;
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

    @Override
    public List<CardProfile> findUsersBySubjectId(int subjectId) {
      return userDao.findUsersBySubjectId(subjectId);
    }

    @Override
    public List<CardProfile> findUsersBySubject(String subject) {
        return userDao.findUsersBySubject(subject);
    }

    public List<User> list() {
        return this.userDao.list();
    }

    @Override
    public User create(String username, String mail) {
        return userDao.create(username, mail);
    }
}
