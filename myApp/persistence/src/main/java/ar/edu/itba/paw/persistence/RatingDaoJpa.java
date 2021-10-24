package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.RatingDao;
import ar.edu.itba.paw.models.Rating;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.utils.Pair;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class RatingDaoJpa implements RatingDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Rating addRating(Long teacherId, Long studentId, float rate, String review) {
        final User teacher = entityManager.getReference(User.class, teacherId);
        final User student = entityManager.getReference(User.class, studentId);
        final Rating rating = new Rating(rate, review, teacher, student);
        entityManager.persist(rating);
        return rating;
    }

    @Override
    public Pair<Float, Integer> getRatingById(Long teacherId) {
        final Query totalQuery = entityManager.createNativeQuery("select count(coalesce(r.rate,0)) from Rating r where r.teacherid = :teacherid");
        totalQuery.setParameter("teacherid", teacherId);
        int totalRatings = ((Number) totalQuery.getSingleResult()).intValue();
        if (totalRatings == 0) return new Pair<>(0F, totalRatings);
        final Query sumQuery = entityManager.createNativeQuery("select sum(r.rate) from Rating r where r.teacherid = :teacherid");
        sumQuery.setParameter("teacherid", teacherId);
        float sumRatings = ((Number) sumQuery.getSingleResult()).floatValue();
        return new Pair<>(sumRatings/totalRatings, totalRatings);
    }
}
