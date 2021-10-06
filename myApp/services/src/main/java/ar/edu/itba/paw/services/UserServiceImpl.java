package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.RoleService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.services.UtilsService;
import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.Pair;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private UserDetailsService userDetailsService;

    @Autowired
    private RoleService roleService;
    
    @Autowired
    private UtilsService utilsService;

    void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public User findById(int id) {
        User u = userDao.get(id);
        if (u != null) {
            List<Role> roles = roleService.getUserRoles(id);
            if (roles == null) return null;
            u.setUserRoles(roles);
            return u;
        }
        return null;
    }

    @Override
    public List<CardProfile> findUsersBySubjectId(int subjectId) {
      return userDao.findUsersBySubjectId(subjectId);
    }


    @Override
    public List<CardProfile> filterUsers(String subject, String order, String price, String level, String rating, String offset) {
        int lvl = Integer.parseInt(level);
        if(lvl < 0 || lvl > MAX_LEVEL)
            lvl = ANY_LEVEL;
        int maxPrice = mostExpensiveUserFee(subject);
        int intPrice = Integer.parseInt(price);
        if (intPrice > maxPrice)
            intPrice = maxPrice;
        return userDao.filterUsers(subject,Integer.parseInt(order),intPrice,lvl,Integer.parseInt(rating),Integer.parseInt(offset));
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
        return userDao.filterUsers(subject,RAND_ORDER,intPrice,lvl,ANY_RATING,GET_ALL);
    }

    @Override
    public List<CardProfile> filterUsers(String subject) {
        return userDao.filterUsers(subject,RAND_ORDER,Integer.MAX_VALUE,ANY_LEVEL,ANY_RATING, GET_ALL);
    }

    @Override
    public List<CardProfile> filterUsers(String subject, String offset) {
        return userDao.filterUsers(subject,RAND_ORDER,Integer.MAX_VALUE,ANY_LEVEL,ANY_RATING, Integer.parseInt(offset));
    }

    @Override
    public Integer getPageQty(String subject, String price, String level, String rating) {
        return userDao.getPageQty( subject,  Integer.parseInt(price),  Integer.parseInt(level),  Integer.parseInt(rating));
    }

    @Override
    public Integer getPageQty(String subject) {
        return userDao.getPageQty(subject,Integer.MAX_VALUE,ANY_LEVEL,ANY_RATING);
    }

    @Override
    public List<CardProfile> getFavourites(int uid) {
        return userDao.getFavourites(uid);
    }

    @Override
    public String getUserSchedule(int userId) {
        User u = userDao.get(userId);
        return u == null ? null : u.getSchedule();
    }

    public List<User> list() {
        return userDao.list();
    }

    @Override
    public Integer mostExpensiveUserFee(String subject) {
        CardProfile mostExpensiveUser;
        List<CardProfile> users = filterUsers(subject);
        if(users != null) {
            mostExpensiveUser = users.stream().max(Comparator.comparing(CardProfile::getMaxPrice)).orElse(null);
            if (mostExpensiveUser != null)
                return mostExpensiveUser.getMaxPrice();
        }
        return 0;
    }

    @Transactional
    @Override
    public Optional<User> create(String username, String mail, String password, String description, String schedule, int userole) {
        User u = userDao.create(utilsService.capitalizeString(username), mail, passwordEncoder.encode(password), description, schedule);
        UserDetails user = userDetailsService.loadUserByUsername(u.getMail());
        Authentication auth = new UsernamePasswordAuthenticationToken(mail, password, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        List<Role> roles = roleService.setUserRoles(u.getId(), userole);
        if (roles != null) {
            u.setUserRoles(roles);
            return Optional.of(u);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String mail) {
        Optional<User> maybe = userDao.findByEmail(mail);
        if (maybe.isPresent()) {
            User u = maybe.get();
            List<Role> roles = roleService.getUserRoles(u.getId());
            if (roles == null) return Optional.empty();
            u.setUserRoles(roles);
            return Optional.of(u);
        }
        return Optional.empty();
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String userMail = authentication.getName();
            Optional<User> maybeUser = this.findByEmail(userMail);
            if (maybeUser.isPresent()) {
                return maybeUser.get();
            }
        }
        return null;
    }

    @Override
    public String getUserDescription(int userId) {
        User u = userDao.get(userId);
        return u == null ? null : u.getDescription();
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
    public boolean isFaved(int teachedId, int studentId) {
        return userDao.isFaved(teachedId, studentId);
    }

    @Override
    public int addRating(int teacherId, int studentId, float rate, String review) {
        return userDao.addRating(teacherId, studentId, rate, review);
    }

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
