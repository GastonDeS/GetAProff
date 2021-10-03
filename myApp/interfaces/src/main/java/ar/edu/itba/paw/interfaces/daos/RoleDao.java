package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Role;

import java.util.List;

public interface RoleDao {

    Role create(String role);

    Role findRoleById(int roleId);

    Role findRoleByName(String role);

    int addRoleToUser(int roleId, int userId);

    List<Role> getUserRoles(int userid);
}
