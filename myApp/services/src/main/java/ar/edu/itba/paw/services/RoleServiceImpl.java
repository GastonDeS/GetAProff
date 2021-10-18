package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.RoleDao;
import ar.edu.itba.paw.interfaces.services.RoleService;
import ar.edu.itba.paw.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Override
    public Role create(String role) {
        return roleDao.create(role);
    }

    @Override
    public Optional<Role> findRoleByName(String role) {
        return roleDao.findRoleByName(role);
    }

    @Override
    public List<Role> getUserRoles(Long userid) {
        return roleDao.getUserRoles(userid);
    }

    @Transactional
    @Override
    public List<Role> setUserRoles(Long userId, int userRole) {
        List<Role> userRoles = new ArrayList<>();
        if (userRole == Roles.TEACHER.id) {
            addRoleToList(userRoles, Roles.TEACHER, userId);
        }
        addRoleToList(userRoles, Roles.STUDENT, userId);
        return userRoles.isEmpty() ? new ArrayList<>() : userRoles;
    }

    @Override
    public int addTeacherRole(Long userId) {
        Optional<Role> teacherRole = findRoleByName(Roles.TEACHER.name);
        return teacherRole.map(role -> roleDao.addRoleToUser(role.getRoleId(), userId)).orElse(0);
    }

    private void addRoleToList(List<Role> userRoles, Roles role, Long userId) {
        Optional<Role> newRole = findRoleByName(role.name);
        if (newRole.isPresent()) {
            userRoles.add(newRole.get());
            roleDao.addRoleToUser(newRole.get().getRoleId(), userId);
        }
    }

    private enum Roles {
        STUDENT("USER_STUDENT", 0),
        TEACHER("USER_TEACHER", 1);

        private String name;
        private int id;

        Roles(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }
}
