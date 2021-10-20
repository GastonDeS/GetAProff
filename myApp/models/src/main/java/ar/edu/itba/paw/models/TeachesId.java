package ar.edu.itba.paw.models;

public class TeachesId {
    private Long teacher, subject;

    private int level;

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
}
