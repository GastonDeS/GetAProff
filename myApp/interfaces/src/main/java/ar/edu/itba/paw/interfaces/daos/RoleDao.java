package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Role;

public interface RoleDao {

    Role create(String role);

    Role findRoleById(int roleId);

    Role findRoleByName(String role);

    int addRoleToUser(int roleId, int userId);
}
