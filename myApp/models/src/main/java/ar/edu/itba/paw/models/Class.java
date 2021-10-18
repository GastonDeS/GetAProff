package ar.edu.itba.paw.models;

public class Class {
    private Long classId, studentId, teacherId, subjectid;
    private int level, price, status, deleted;
    private String messageRequest, messageReply;

    public Class(Long classId, Long studentId, Long teacherId, int level, Long subjectid, int price, int status, String messageRequest, String messageReply, int deleted) {
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

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Long getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(Long subjectid) {
        this.subjectid = subjectid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

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
        CANCELEDS(3),
        CANCELEDT(4),
        REJECTED(5),
        RATED(6),
        ;

        private final int value;

        public int getValue() {
            return value;
        }

        Status(int value) {
            this.value = value;
        }
    }

    public enum Deleted{
        NO(0),
        STUDENT(1),
        TEACHER(2),
        BOTH(3);

        private final int value;

        public int getValue() {
            return value;
        }

        Deleted(int value) {
            this.value = value;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}
