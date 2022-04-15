package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Lecture;

public class ClassroomDto {
//    private String

    private String subjectName;
    private Long studentId, teacherId, classId;
    private int level, price, status;


    public static ClassroomDto getClassroom(Lecture lecture) {
        ClassroomDto classroomDto = new ClassroomDto();
        classroomDto.studentId = lecture.getStudent().getId();
        classroomDto.teacherId = lecture.getTeacher().getId();
        classroomDto.classId = lecture.getClassId();
        classroomDto.subjectName = lecture.getSubject().getName();
        classroomDto.price = lecture.getPrice();
        classroomDto.status = lecture.getStatus();
        return classroomDto;
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

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
