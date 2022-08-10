package ar.edu.itba.paw.models;

import java.io.Serializable;
import java.util.Objects;

public class RatingId implements Serializable {

    private Long teacher, student;

    RatingId() {
        //Just for hibernate
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingId ratingId = (RatingId) o;
        return Objects.equals(teacher, ratingId.teacher) && Objects.equals(student, ratingId.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacher, student);
    }
}
