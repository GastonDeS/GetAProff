package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Rating;
import ar.edu.itba.paw.models.utils.Pair;

public interface RatingDao {

    Rating addRating(Long teacherId, Long studentId, float rate, String review);

    Pair<Float, Integer> getRatingById(Long teacherId);
}
