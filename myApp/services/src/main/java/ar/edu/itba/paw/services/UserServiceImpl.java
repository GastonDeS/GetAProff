package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.InsertException;
import ar.edu.itba.paw.models.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findById(Long userId) {
        return userDao.get(userId);
    }

    @Transactional
    @Override
    public Page<TeacherInfo> getFavourites(Long userId, Integer page, Integer pageSize) {
        return userDao.getFavourites(userId, new PageRequest(page, pageSize));
    }

    @Transactional
    @Override
    public Optional<User> create(String username, String mail, String password, String description, String schedule, Long roleId) {
        User user = userDao.create(Utility.capitalizeString(username), mail, passwordEncoder.encode(password), description, schedule);
        List<UserRole> userRoles = new ArrayList<>();
        if (roleId.equals(Roles.TEACHER.getId())) {
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

    @Transactional
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
