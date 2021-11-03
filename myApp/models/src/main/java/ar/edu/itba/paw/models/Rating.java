package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "rating")
@IdClass(RatingId.class)
public class Rating {

    @Column
    private Float rate;

    @Column
    private String review;

    @JoinColumn(name = "teacherid", referencedColumnName = "userid")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @Id
    private User teacher;

    @JoinColumn(name = "userid", referencedColumnName = "userid")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @Id
    private User student;

    Rating() {
        //Just for hibernate
    }

    public Rating(Float rate, String review, User teacher, User student) {
        this.rate = rate;
        this.review = review;
        this.teacher = teacher;
        this.student = student;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }
}
