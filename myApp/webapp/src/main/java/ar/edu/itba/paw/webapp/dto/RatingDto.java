package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Rating;
import ar.edu.itba.paw.models.RatingId;

public class RatingDto {

    private String student, review;

    private float rate;

    private Long teacherId;

    public static RatingDto fromRating(Rating rating) {
        RatingDto ratingDto = new RatingDto();
        ratingDto.rate = rating.getRate();
        ratingDto.review = rating.getReview();
        ratingDto.student = rating.getStudent().getName();
        ratingDto.teacherId = rating.getTeacher().getId();
        return ratingDto;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
