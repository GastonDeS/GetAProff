package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.RoleDao;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
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
        final TypedQuery<Role> query = entityManager.createQuery("from Role where role = :role", Role.class);
        query.setParameter("role", role);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public User addRoleToUser(Long roleId, Long userId) {
        User user = entityManager.find(User.class, userId);
        Role role = entityManager.find(Role.class, roleId);
        user.getUserRoles().add(role);
        entityManager.persist(user);
        return user;
    }

    //TODO: BORRAR PORQUE NO USAMOS
    @Override
    public List<Role> getUserRoles(Long userid) {
        return new ArrayList<>();
    }
}
