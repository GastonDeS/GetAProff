package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.InsertException;
import ar.edu.itba.paw.models.utils.Utility;
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

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

//    void setRoleService(RoleService roleService) {
//        this.roleService = roleService;
//    }

    @Override
    public Optional<User> findById(Long userId) {
        return userDao.get(userId);
    }

    @Transactional
    @Override
    public List<CardProfile> getFavourites(Long userId) {
        List<Object> favourites = userDao.getFavourites(userId);
        List<CardProfile> teacherCard = new ArrayList<>();
        favourites.forEach((favourite) -> {
            Object[] cardProfileInfo = (Object[]) favourite;
            teacherCard.add(
                    new CardProfile(((Number) cardProfileInfo[0]).longValue(), cardProfileInfo[1].toString(), ((Number) cardProfileInfo[2]).intValue(),
                            ((Number) cardProfileInfo[3]).intValue(), cardProfileInfo[4].toString(), ((Number) cardProfileInfo[5]).floatValue())
            );
        });
        return teacherCard;
    }

    @Transactional
    @Override
    public Optional<User> create(String username, String mail, String password, String description, String schedule, Long roleid) {
        User user = userDao.create(Utility.capitalizeString(username), mail, passwordEncoder.encode(password), description, schedule);
        List<UserRole> userRoles = new ArrayList<>();
        if (roleid.equals(Roles.TEACHER.getId())) {
            userRoles.add(new UserRole(Roles.TEACHER.getId(), user));
        }
        userRoles.add(new UserRole(Roles.STUDENT.getId(), user));
        user.setUserRoles(userRoles);
        return Optional.of(user);
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

    @Override
    public void setTeacherAuthorityToUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        updatedAuthorities.add(new SimpleGrantedAuthority(Roles.TEACHER.getName()));

        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
