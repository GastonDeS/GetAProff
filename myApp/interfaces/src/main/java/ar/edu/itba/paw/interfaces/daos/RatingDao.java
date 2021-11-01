package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Rating;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.utils.Pair;

import java.util.List;

public interface RatingDao {

    Rating addRating(User teacher, User student, float rate, String review);

    Pair<Float, Integer> getRatingById(Long teacherId);

    List<Rating> getTeacherRatings(Long teacherId);
}
