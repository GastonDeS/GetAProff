package ar.edu.itba.paw.webapp.requestDto;

import javax.validation.constraints.NotNull;

public class NewRatingDto {

    @NotNull
    private float rate;

    private String review;

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
