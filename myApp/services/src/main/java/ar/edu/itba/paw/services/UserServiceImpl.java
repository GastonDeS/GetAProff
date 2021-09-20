package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.smartcardio.Card;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    public static final Integer ANY_LEVEL = 0;
    public static final Integer MAX_LEVEL = 3;

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
    public List<CardProfile> filterUsers(String subject, String price, String level) {
        int lvl = Integer.parseInt(level);
        if(lvl < 0 || lvl > MAX_LEVEL)
            lvl = ANY_LEVEL;
        int maxPrice = mostExpensiveUserFee(subject);
        int intPrice = Integer.parseInt(price);
            if (intPrice > maxPrice)
                intPrice = maxPrice;
        return userDao.filterUsers(subject, intPrice,lvl);
    }

    @Override
    public List<CardProfile> filterUsers(String subject) {
        return userDao.filterUsers(subject,Integer.MAX_VALUE,ANY_LEVEL);
    }

    public List<User> list() {
        return this.userDao.list();
    }

    @Override
    public Integer mostExpensiveUserFee(String subject) {
        CardProfile mostExpensiveUser;
        List<CardProfile> users = filterUsers(subject);
        if(users != null) {
            mostExpensiveUser = users.stream().max(Comparator.comparing(CardProfile::getPrice)).orElse(null);
            if (mostExpensiveUser != null)
                return mostExpensiveUser.getPrice();
        }
        return 0;
    }

    @Override
    public User create(String username, String mail, String password, int userRole) {
        return userDao.create(username, mail, passwordEncoder.encode(password), userRole);
    }

    @Override
    public Optional<User> findByEmail(String mail) {
        return userDao.findByEmail(mail);
    }
}
