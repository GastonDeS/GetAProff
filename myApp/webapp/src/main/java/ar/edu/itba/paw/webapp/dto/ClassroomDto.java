package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Lecture;

import javax.ws.rs.core.UriInfo;

public class ClassroomDto {
//    private String

    private String subjectName, posts, files;
    private Long studentId, teacherId, classId;
    private int level, price, status;



    public static ClassroomDto getClassroom(UriInfo uri, Lecture lecture) {
        ClassroomDto classroomDto = new ClassroomDto();
        classroomDto.studentId = lecture.getStudent().getId();
        classroomDto.teacherId = lecture.getTeacher().getId();
        classroomDto.classId = lecture.getClassId();
        classroomDto.subjectName = lecture.getSubject().getName();
        classroomDto.price = lecture.getPrice();
        classroomDto.status = lecture.getStatus();
        classroomDto.posts = uri.getBaseUriBuilder().path(uri.getPath()+"/posts").build().toString();
        classroomDto.files = uri.getBaseUriBuilder().path(uri.getPath()+"/files").build().toString();
        return classroomDto;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
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
