package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public Optional<User> create(String username, String mail, String password, int userRole) {
        return Optional.of(userDao.create(username, mail, passwordEncoder.encode(password), userRole));
    }

    @Override
    public Optional<User> findByEmail(String mail) {
        return userDao.findByEmail(mail);
    }
}
