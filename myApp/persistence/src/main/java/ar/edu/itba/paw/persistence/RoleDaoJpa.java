package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.RoleDao;
import ar.edu.itba.paw.models.Role;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class RoleDaoJpa implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role create(String role) {
        final Role newRole = new Role(null, role);
        entityManager.persist(newRole);
        return newRole;
    }

    @Override
    public Optional<Role> findRoleByName(String role) {
        return Optional.empty();
    }

    @Override
    public int addRoleToUser(Long roleId, Long userId) {
        return 0;
    }

    @Override
    public List<Role> getUserRoles(Long userid) {
        return null;
    }
}
