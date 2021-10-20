package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.RoleDao;
import ar.edu.itba.paw.interfaces.services.RoleService;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        //TODO: BORRAR PORQUE YA NO SIRVE
        return roleDao.getUserRoles(userid);
    }

    @Transactional
    @Override
    public List<Role> setUserRoles(Long userId, Long userRole) {
        List<Role> userRoles = new ArrayList<>();
        if (Objects.equals(userRole, Roles.TEACHER.id)) {
            addRoleToList(userRoles, Roles.TEACHER);
        }
        addRoleToList(userRoles, Roles.STUDENT);
        return userRoles.isEmpty() ? new ArrayList<>() : userRoles;
    }

    @Transactional
    @Override
    public Boolean addTeacherRole(Long userId) {
        Optional<Role> teacherRole = findRoleByName(Roles.TEACHER.name);
        if (teacherRole.isPresent()) {
            User user = roleDao.addRoleToUser(teacherRole.get().getRoleId(), userId);
            return user.isTeacher();
        }
        return false;
    }

    private void addRoleToList(List<Role> userRoles, Roles role) {
        Optional<Role> newRole = findRoleByName(role.name);
        newRole.ifPresent(userRoles::add);
    }

    private enum Roles {
        STUDENT("USER_STUDENT", 0L),
        TEACHER("USER_TEACHER", 1L);

        private String name;
        private Long id;

        Roles(String name, Long id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public Long getId() {
            return id;
        }
    }
}
