package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.UserRoleDao;
import ar.edu.itba.paw.interfaces.services.UserRoleService;
import ar.edu.itba.paw.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Transactional
    @Override
    public Optional<UserRole> addRoleToUser(Long userId, Long roleId) {
        return Optional.ofNullable(userRoleDao.addRoleToUser(userId, roleId));
    }
}
