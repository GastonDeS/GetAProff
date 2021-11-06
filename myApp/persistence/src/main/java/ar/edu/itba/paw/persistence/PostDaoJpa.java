package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.PostDao;
import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.models.Post;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Repository
public class PostDaoJpa implements PostDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Post post(Long uploaderId, Long classId, String filename, byte[] file, String message, String type) {
        final User uploader = entityManager.getReference(User.class, uploaderId);
        final Lecture associatedLecture = entityManager.getReference(Lecture.class, classId);
        final Post post = new Post(null, associatedLecture, uploader, message, filename, file, type, Timestamp.from(Instant.now()));
        entityManager.persist(post);
        return post;
    }

    @Override
    public List<Post> retrievePosts(Long classId) {
        final TypedQuery<Lecture> query = entityManager.createQuery("from Lecture c where c.id = :classId", Lecture.class);
        query.setParameter("classId", classId);
        return query.getSingleResult().getClassPosts();
    }

    @Override
    public Post getPostById(Long postId) {
        return entityManager.getReference(Post.class, postId);
    }
}
