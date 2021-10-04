package ar.edu.itba.paw.models;

public class Class {
    private int classId, studentId, teacherId, level, subjectid, price, status;
    private String messageRequest, messageReply;

    public Class(int classId, int studentId, int teacherId, int level, int subjectid, int price, int status, String messageRequest, String messageReply) {
        this.classId = classId;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.level = level;
        this.subjectid = subjectid;
        this.price = price;
        this.status = status;
        this.messageRequest = messageRequest;
        this.messageReply = messageReply;
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

    public int getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(int subjectid) {
        this.subjectid = subjectid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    //    public User getStudent() {
//        return student;
//    }
//
//    public void setStudent(User student) {
//        this.student = student;
//    }
//
//    public User getTeacher() {
//        return teacher;
//    }
//
//    public void setTeacher(User teacher) {
//        this.teacher = teacher;
//    }
//
//    public SubjectInfo getSubject() {
//        return subject;
//    }
//
//    public void setSubject(SubjectInfo subject) {
//        this.subject = subject;
//    }

    public String getMessageRequest() {
        return messageRequest;
    }

    public void setMessageRequest(String messageRequest) {
        this.messageRequest = messageRequest;
    }

    public String getMessageReply() {
        return messageReply;
    }

    public void setMessageReply(String messageReply) {
        this.messageReply = messageReply;
    }

    public enum Status{
        PENDING(0),
        ACCEPTED(1),
        FINISHED(2),
        CANCELED(3),
        REJECTED(4),
        RATED(5);

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
