package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Rating;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.utils.Pair;

import java.util.List;
import java.util.Optional;

public interface RatingService {

    Pair<Float, Integer> getRatingById(Long teacherId);

    Optional<Rating> addRating(User teacher, User student, float rate, String review);

    List<Rating> getTeacherRatings(Long teacherId);
}
