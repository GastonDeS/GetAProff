package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.ImageDao;
import ar.edu.itba.paw.models.Image;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Primary
@Repository
public class ImageDaoJpa implements ImageDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Image createOrUpdate(Long uid, byte[] image) {
        final Image newImage = new Image(null,image);
        entityManager.persist(newImage);
        return newImage;
    }

    @Override
    public Optional<Image> findImageById(Long userId) {
        return Optional.empty();
    }
}
