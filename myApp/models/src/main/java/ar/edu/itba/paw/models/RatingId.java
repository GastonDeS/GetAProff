package ar.edu.itba.paw.models;

import java.io.Serializable;

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
}
