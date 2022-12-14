package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.Rating;
import ar.edu.itba.paw.models.utils.Pair;

import java.util.Optional;

public interface RatingService {

    Optional<Rating> addRating(Long teacherId, Long studentId, float rate, String review);

    Page<Rating> getTeacherRatings(Long teacherId, Integer page, Integer pageSize);

    boolean availableToRate(Long teacherId, Long studentId);
}
