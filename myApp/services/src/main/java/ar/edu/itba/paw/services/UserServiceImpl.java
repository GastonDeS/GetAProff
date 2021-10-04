package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.RoleService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.CardProfile;
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

    public static final Integer ANY_LEVEL = 0;
    public static final Integer MAX_LEVEL = 3;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RoleService roleService;

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
    public Optional<User> create(String username, String mail, String password, int userole) {
        User u = userDao.create(username, mail, passwordEncoder.encode(password));
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

    @Transactional
    @Override
    public int removeFavourite(int teacherId, int studentId) {
        return userDao.removeFavourite(teacherId, studentId);
    }
}
