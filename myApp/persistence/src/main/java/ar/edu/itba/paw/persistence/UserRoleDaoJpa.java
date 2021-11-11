package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.UserRoleDao;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserRoleDaoJpa implements UserRoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean addRoleToUser(Long userId, Long roleId) {
        final User user = entityManager.getReference(User.class, userId);
        final UserRole userRole = new UserRole(roleId, user);
        user.getUserRoles().add(userRole);
        return user.getUserRoles().contains(userRole);
    }
}
