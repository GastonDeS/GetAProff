package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.PostDao;
import ar.edu.itba.paw.models.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.time.Instant;

@Repository
public class PostDaoJpa extends BasePaginationDaoImpl<Post> implements PostDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Post post(Long uploaderId, Long classId, String filename, byte[] file, String message, String type) {
        final User uploader = entityManager.getReference(User.class, uploaderId);
        final Lecture associatedLecture = entityManager.getReference(Lecture.class, classId);
        final Post post = new Post(null, associatedLecture, uploader, message, filename, file, type, Timestamp.from(Instant.now()));
        entityManager.persist(post);
        return post;
    }

    @Override
    public Page<Post> retrievePosts(Long classId, PageRequest pageRequest) {
        final Lecture lecture = entityManager.getReference(Lecture.class, classId);
        final TypedQuery<Post> query = entityManager.createQuery("from Post p where p.associatedLecture = :lecture order by time DESC", Post.class);
        query.setParameter("lecture", lecture);
        return listBy(query, pageRequest);
    }

    @Override
    public Post getPostById(Long postId) {
        return entityManager.getReference(Post.class, postId);
    }
}
