package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.PostDao;
import ar.edu.itba.paw.models.Class;
import ar.edu.itba.paw.models.Post;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PostDaoJpa implements PostDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Post post(Long uploaderId, Long classId, String filename, byte[] file, String message, String type) {
        final User uploader = entityManager.getReference(User.class, uploaderId);
        final Class associatedClass = entityManager.getReference(Class.class, classId);
        final Post post = new Post(null, associatedClass, uploader, message, filename, file, type);
        entityManager.persist(post);
        return post;
    }

    @Override
    public List<Post> retrievePosts(Long classId) {
        final Class associatedClass = entityManager.getReference(Class.class, classId);
        final TypedQuery<Post> query = entityManager.createQuery("from Post p where p.associatedClass = :class", Post.class);
        query.setParameter("class", associatedClass);
        return query.getResultList();
    }

    @Override
    public Post getPostById(Long postId) {
        return entityManager.getReference(Post.class, postId);
    }
}
