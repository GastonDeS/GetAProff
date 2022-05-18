package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.UserRole;

import java.util.Optional;

public interface UserRoleService {

    Optional<UserRole> addRoleToUser(Long userId, Long roleId);

}
