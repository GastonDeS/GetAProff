package ar.edu.itba.paw.models;

import java.io.Serializable;

public class TeachesId implements Serializable {
    private Long teacher, subject;

    private int level;

    TeachesId() {
        //Just for hibernate
    }

    public Long getTeacher() {
        return teacher;
    }

    public void setTeacher(Long teacher) {
        this.teacher = teacher;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
