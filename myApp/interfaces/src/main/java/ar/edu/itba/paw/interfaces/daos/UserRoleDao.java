package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.UserRole;

public interface UserRoleDao {

    boolean addRoleToUser(Long userId, Long roleId);
}
