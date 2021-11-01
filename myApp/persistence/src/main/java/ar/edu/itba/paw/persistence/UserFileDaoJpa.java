package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.UserFileDao;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserFile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserFileDaoJpa implements UserFileDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserFile> getAllUserFiles(Long ownerId) {
        final User owner = entityManager.getReference(User.class, ownerId);
        TypedQuery<UserFile> query = entityManager.createQuery("from UserFile u where u.fileOwner = :owner",UserFile.class);
        query.setParameter("owner", owner);
        return query.getResultList();
    }

    @Override
    public UserFile saveNewFile(byte[] file, String fileName, Long ownerId) {
        final User owner = entityManager.getReference(User.class, ownerId);
        final UserFile newFile = new UserFile(owner, fileName, file);
        entityManager.persist(newFile);
        return newFile;
    }
}
