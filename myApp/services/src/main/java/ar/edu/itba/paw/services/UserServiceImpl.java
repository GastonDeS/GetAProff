package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.CardProfile;
import ar.edu.itba.paw.models.exceptions.InsertException;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private TeachesService teachesService;

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

    @Transactional
    @Override
    public List<CardProfile> getFavourites(Long userId) {
        List<User> favourites = userDao.getFavourites(userId);
        List<CardProfile> teacherCard = new ArrayList<>();
        for (User teacher : favourites) {
            Long teacherId = teacher.getId();
            Float rate = ratingService.getRatingById(teacherId).getValue1();
            CardProfile cardProfile = new CardProfile(teacherId, teacher.getName(), teachesService.getMaxPrice(teacherId),
                    teachesService.getMinPrice(teacherId), teacher.getDescription(), imageService.hasImage(teacherId), rate);
            teacherCard.add(cardProfile);
        }
        return teacherCard;
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
            System.out.println("SIZE AUTH" + Arrays.toString(authentication.getAuthorities().toArray()));
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

    @Override
    public void setTeacherAuthorityToUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        updatedAuthorities.add(new SimpleGrantedAuthority("USER_TEACHER"));

        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
