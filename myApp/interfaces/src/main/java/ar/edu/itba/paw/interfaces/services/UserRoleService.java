package ar.edu.itba.paw.interfaces.services;

public interface UserRoleService {

    boolean addRoleToUser(Long userId, Long roleId);

}
