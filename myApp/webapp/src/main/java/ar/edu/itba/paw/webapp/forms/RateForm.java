package ar.edu.itba.paw.webapp.forms;

import javax.validation.constraints.NotNull;

public class RateForm {

    private String review;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @NotNull
    private Integer rating;

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }


}
