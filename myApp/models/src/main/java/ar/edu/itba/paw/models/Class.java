package ar.edu.itba.paw.models;

public class Class {
    private int classId;
    private User student, teacher;
    private SubjectInfo subject;
    private int status;

    public Class(int classId, User student, User teacher, SubjectInfo subject, int status) {
        this.classId = classId;
        this.student = student;
        this.teacher = teacher;
        this.subject = subject;
        this.status = status;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public SubjectInfo getSubject() {
        return subject;
    }

    public void setSubject(SubjectInfo subject) {
        this.subject = subject;
    }

    public enum Status{
        PENDING(0),
        ACCEPTED(1),
        FINISHED(2),
        CANCELED(3),
        REJECTED(4);

        private final int value;

        public int getValue() {
            return value;
        }

        Status(int value) {
            this.value = value;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
