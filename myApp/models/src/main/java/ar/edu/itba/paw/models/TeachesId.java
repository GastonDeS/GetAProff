package ar.edu.itba.paw.models;

import java.io.Serializable;
import java.util.Objects;

public class TeachesId implements Serializable {
    private Long teacher, subject;

    private int level;

    TeachesId() {
        //Just for hibernate
    }

    public TeachesId(Long teacher, Long subject, int level) {
        this.teacher = teacher;
        this.subject = subject;
        this.level = level;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeachesId teachesId = (TeachesId) o;
        return level == teachesId.level && Objects.equals(teacher, teachesId.teacher) && Objects.equals(subject, teachesId.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacher, subject, level);
    }
}
