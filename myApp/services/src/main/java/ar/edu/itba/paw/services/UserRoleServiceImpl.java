package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.UserRoleDao;
import ar.edu.itba.paw.interfaces.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public boolean addRoleToUser(Long userId, Long roleId) {
        return userRoleDao.addRoleToUser(userId, roleId);
    }
}
