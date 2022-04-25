package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.RatingDao;
import ar.edu.itba.paw.interfaces.services.RatingService;
import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.PageRequest;
import ar.edu.itba.paw.models.Rating;
import ar.edu.itba.paw.models.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingDao ratingDao;

    @Override
    public Pair<Float, Integer> getRatingById(Long teacherId) {
        return ratingDao.getRatingById(teacherId);
    }

    @Transactional
    @Override
    public Optional<Rating> addRating(Long teacherId, Long studentId, float rate, String review) {
        return Optional.ofNullable(ratingDao.addRating(teacherId, studentId, rate, review));
    }

    @Override
    public Page<Rating> getTeacherRatings(Long teacherId, Integer page, Integer pageSize) {
        return ratingDao.getTeacherRatings(teacherId, new PageRequest(page, pageSize));
    }
}
