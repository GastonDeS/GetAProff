package ar.edu.itba.paw.models;

public class Class {
    private int classId, studentId, teacherId, level, subjectId, price, status;

    public Class(int classId, int studentId, int teacherId, int level, int subjectId, int price, int status) {
        this.classId = classId;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.level = level;
        this.subjectId = subjectId;
        this.price = price;
        this.status = status;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public enum Status{
        PENDING(0),
        ACCEPTED(1),
        FINISHED(2),
        CANCELED(3);

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
