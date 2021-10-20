package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ImageDao;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Primary
@Repository
public class ImageDaoJpa implements ImageDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Image createOrUpdate(Long uid, byte[] image) {
        User user = entityManager.find(User.class,uid);
        final Image newImage = new Image(uid,image);
        newImage.setUser(user);
        user.setImage(newImage);
        entityManager.persist(newImage);
        return newImage;
    }

    @Override
    public Optional<Image> findImageById(Long userId) {
        final TypedQuery<Image> query = entityManager.createQuery("from Image i where i.userid = :id", Image.class);
        query.setParameter("id", userId);
        return query.getResultList().stream().findFirst();
    }
}
