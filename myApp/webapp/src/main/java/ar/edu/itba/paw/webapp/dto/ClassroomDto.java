package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.webapp.controller.ClassroomController;
import ar.edu.itba.paw.webapp.controller.PostFileController;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;

import javax.ws.rs.core.UriInfo;

public class ClassroomDto {

    private String subjectName, posts, files;
    private Long classId;
    private StudentDto teacher;
    private StudentDto student;
    private int level, price, status, notifications;



    public static ClassroomDto getClassroom(Lecture lecture) {
        ClassroomDto classroomDto = new ClassroomDto();
        classroomDto.student = StudentDto.fromUser(lecture.getStudent());
        classroomDto.teacher = StudentDto.fromUser(lecture.getTeacher());
        classroomDto.classId = lecture.getClassId();
        classroomDto.subjectName = lecture.getSubject().getName();
        classroomDto.price = lecture.getPrice();
        classroomDto.status = lecture.getStatus();
        classroomDto.level = lecture.getLevel();
        //classroomDto.posts = uri.getBaseUriBuilder().path(uri.getPath()+"posts").build().toString();
        classroomDto.posts = JaxRsLinkBuilder.linkTo(ClassroomController.class).slash(lecture.getClassId()).slash("posts").toString();
        //classroomDto.files = uri.getBaseUriBuilder().path(uri.getPath()+"files").build().toString();
        classroomDto.files = JaxRsLinkBuilder.linkTo(PostFileController.class).slash(lecture.getClassId()).slash("files").toString();
        //classroomDto.notifications = lecture.getNotifications();
        return classroomDto;
    }

    public int getNotifications() {
        return notifications;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
    }

    public StudentDto getStudent() {
        return student;
    }

    public StudentDto getTeacher() {
        return teacher;
    }

    public void setStudent(StudentDto student) {
        this.student = student;
    }

    public void setTeacher(StudentDto teacher) {
        this.teacher = teacher;
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
