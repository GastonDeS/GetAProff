package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role create(String role);

    Optional<Role> findRoleByName(String role);

    List<Role> setUserRoles(int userId, int userRole);

    List<Role> getUserRoles(int userid);
}
