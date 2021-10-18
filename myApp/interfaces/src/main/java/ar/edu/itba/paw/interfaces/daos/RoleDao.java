package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDao {

    Role create(String role);

    Optional<Role> findRoleById(int roleId);

    Optional<Role> findRoleByName(String role);

    int addRoleToUser(int roleId, Long userId);

    List<Role> getUserRoles(Long userid);
}
