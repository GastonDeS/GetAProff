package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Rating;
import ar.edu.itba.paw.models.utils.Pair;

import java.util.Optional;

public interface RatingService {

    Pair<Float, Integer> getRatingById(Long teacherId);

    Optional<Rating> addRating(Long teacherId, Long studentId, float rate, String review);
}
