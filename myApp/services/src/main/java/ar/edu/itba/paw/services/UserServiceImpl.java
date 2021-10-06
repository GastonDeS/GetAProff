package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.RoleService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.services.UtilsService;
import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.utils.Pair;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    public static final Integer ANY_LEVEL = 0, ANY_RATING = 0, RAND_ORDER = 0,MAX_LEVEL = 3, GET_ALL = 0;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UtilsService utilsService;

    @Override
    public Optional<User> findById(int id) {
        Optional<User> maybeUser = userDao.get(id);
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            Optional<List<Role>> roles = roleService.getUserRoles(id);
            if (!roles.isPresent()) {
                return Optional.empty();
            }
            user.setUserRoles(roles.get());
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<CardProfile>> filterUsers(String subject, String order, String price, String level, String rating, String offset) {
        int lvl = Integer.parseInt(level);
        if(lvl < 0 || lvl > MAX_LEVEL)
            lvl = ANY_LEVEL;
        Integer maxPrice = mostExpensiveUserFee(subject);
        int intPrice = Integer.parseInt(price);
        if (intPrice > maxPrice)
            intPrice = maxPrice;
        return userDao.filterUsers(subject,Integer.parseInt(order),intPrice,lvl,Integer.parseInt(rating),Integer.parseInt(offset));
    }

    @Override
    public Optional<List<CardProfile>> filterUsers(String subject, String price, String level) {
        int lvl = Integer.parseInt(level);
        if(lvl < 0 || lvl > MAX_LEVEL)
            lvl = ANY_LEVEL;
        Integer maxPrice = mostExpensiveUserFee(subject);
        int intPrice = Integer.parseInt(price);
            if (intPrice > maxPrice)
                intPrice = maxPrice;
        return userDao.filterUsers(subject,RAND_ORDER,intPrice,lvl,ANY_RATING,GET_ALL);
    }

    @Override
    public Optional<List<CardProfile>> filterUsers(String subject) {
        return userDao.filterUsers(subject,RAND_ORDER,Integer.MAX_VALUE,ANY_LEVEL,ANY_RATING, GET_ALL);
    }

    @Override
    public Optional<List<CardProfile>> filterUsers(String subject, String offset) {
        return userDao.filterUsers(subject,RAND_ORDER,Integer.MAX_VALUE,ANY_LEVEL,ANY_RATING, Integer.parseInt(offset));
    }

    @Override
    public Optional<List<CardProfile>> getFavourites(int uid) {
        return userDao.getFavourites(uid);
    }

    @Override
    public Optional<String> getUserSchedule(int userId) {
        Optional<User> u = userDao.get(userId);
        return u.map(User::getSchedule);
    }

    public List<User> list() {
        return userDao.list();
    }

    @Override
    public Integer mostExpensiveUserFee(String subject) {
        Optional<CardProfile> mostExpensiveUser;
        Optional<List<CardProfile>> users = filterUsers(subject);
        if(!users.isPresent()) {
            return 1;
        }
        mostExpensiveUser = users.get().stream().max(Comparator.comparing(CardProfile::getMaxPrice));
        return mostExpensiveUser.map(CardProfile::getMaxPrice).orElse(1);
    }

    @Transactional
    @Override
    public Optional<User> create(String username, String mail, String password, String description, String schedule, int userole) {
        User u = userDao.create(utilsService.capitalizeString(username), mail, passwordEncoder.encode(password), description, schedule);
        Optional<List<Role>> roles = roleService.setUserRoles(u.getId(), userole);
        if (!roles.isPresent()) {
            return Optional.empty();
        }
        u.setUserRoles(roles.get());
        return Optional.of(u);
    }

    @Override
    public Optional<User> findByEmail(String mail) {
        Optional<User> maybe = userDao.findByEmail(mail);
        if (maybe.isPresent()) {
            User u = maybe.get();
            Optional<List<Role>> roles = roleService.getUserRoles(u.getId());
            if (!roles.isPresent()) {
                return Optional.empty();
            }
            u.setUserRoles(roles.get());
            return Optional.of(u);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String userMail = authentication.getName();
            return this.findByEmail(userMail);
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getUserDescription(int userId) {
        Optional<User> u = userDao.get(userId);
        return u.map(User::getDescription);
    }

    @Transactional
    @Override
    public int setUserSchedule(int userId, String schedule){
        return userDao.setUserSchedule(userId, schedule);
    }

    @Transactional
    @Override
    public int setUserDescription(int userId, String description) {
        return userDao.setUserDescription(userId, description);
    }

    @Transactional
    @Override
    public int addFavourite(int teacherId, int studentId) {
        return userDao.addFavourite(teacherId, studentId);
    }

    @Override
    public boolean isFaved(int teacherId, int studentId) {
        Optional<String> count = userDao.isFaved(teacherId, studentId);
        return count.map(s -> s.equals("1")).orElse(false);
    }

    @Override
    public int addRating(int teacherId, int studentId, float rate, String review) {
        return userDao.addRating(teacherId, studentId, rate, review);
    }

    //Donde se usa?
    @Override
    public Pair<Float, Integer> getRatingById(int teacherId) {
        return userDao.getRatingById(teacherId);
    }

    @Transactional
    @Override
    public int removeFavourite(int teacherId, int studentId) {
        return userDao.removeFavourite(teacherId, studentId);
    }
}
