package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.daos.UserRoleDao;
import ar.edu.itba.paw.interfaces.services.UserRoleService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public Optional<UserRole> addRoleToUser(Long userId, Long roleId) {
        Optional<UserRole> userRole = Optional.ofNullable(userRoleDao.addRoleToUser(userId, roleId));
        if (!userRole.isPresent()) return Optional.empty();
        Optional<User> user = userDao.get(userId);
        if (!user.isPresent()) return Optional.empty();
        user.get().getUserRoles().add(userRole.get());
        return userRole;
    }
}
