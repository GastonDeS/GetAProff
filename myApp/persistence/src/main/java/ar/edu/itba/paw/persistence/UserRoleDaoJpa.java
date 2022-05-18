package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.UserRoleDao;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class UserRoleDaoJpa implements UserRoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserRole addRoleToUser(Long userId, Long roleId) {
        final Optional<User> user = Optional.ofNullable(entityManager.getReference(User.class, userId));
        if (!user.isPresent()) return null;
        final UserRole userRole = new UserRole(roleId, user.get());
        entityManager.persist(userRole);
        user.get().getUserRoles().add(userRole);
        return userRole;
    }
}
