package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.RoleService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.services.UtilsService;
import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.exceptions.InsertException;
import ar.edu.itba.paw.models.utils.Pair;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    void setUtilsService(UtilsService utilsService) {
        this.utilsService = utilsService;
    }

    void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userDao.get(userId);
    }

    @Override
    public List<CardProfile> filterUsers(String subject, String order, String price, String level, String rating, String offset) {
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
    public List<CardProfile> getFavourites(Long uid) {
        List<User> favourites = userDao.getFavourites(uid);
        List<CardProfile> teacherCard = new ArrayList<>();
        for (User teacher : favourites) {

            CardProfile cardProfile = new CardProfile(teacher.getId(), teacher.getName(), 0, 0, teacher.getDescription(), 0, 0);
        }
        return teacherCard;
    }

    @Override
    public Integer mostExpensiveUserFee(String subject) {
        Optional<CardProfile> mostExpensiveUser;
        List<CardProfile> users = filterUsers(subject);
        if(users.isEmpty()) {
            return 1;
        }
        mostExpensiveUser = users.stream().max(Comparator.comparing(CardProfile::getMaxPrice));
        return mostExpensiveUser.map(CardProfile::getMaxPrice).orElse(1);
    }

    @Transactional
    @Override
    public Optional<User> create(String username, String mail, String password, String description, String schedule, Long userole) {
        User u = userDao.create(utilsService.capitalizeString(username), mail, passwordEncoder.encode(password), description, schedule);
        List<Role> roles = roleService.setUserRoles(u.getId(), userole);
        if (roles.isEmpty()) {
            return Optional.empty();
        }
        u.setUserRoles(roles);
        return Optional.of(u);
    }

    @Transactional
    @Override
    public Optional<User> findByEmail(String mail) {
        return userDao.findByEmail(mail);
    }

    @Override
    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            String userMail = authentication.getName();
            return this.findByEmail(userMail);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public int setUserSchedule(Long userId, String schedule){
        return userDao.setUserSchedule(userId, schedule);
    }

    @Transactional
    @Override
    public int setUserDescription(Long userId, String description) {
        return userDao.setUserDescription(userId, description);
    }

    @Transactional
    @Override
    public int setUserName(Long userId, String name){
        return userDao.setUserName(userId, name);
    }

    @Transactional
    @Override
    public int addFavourite(Long teacherId, Long studentId) {
        int modified;
        try {
            modified = userDao.addFavourite(teacherId, studentId);
        } catch (DuplicateKeyException exception) {
            throw new InsertException("Unable to insert");
        }
        return modified;
    }

    @Override
    public boolean isFaved(Long teacherId, Long studentId) {
        return userDao.isFaved(teacherId, studentId);
    }

    @Transactional
    @Override
    public int removeFavourite(Long teacherId, Long studentId) {
        return userDao.removeFavourite(teacherId, studentId);
    }
}
