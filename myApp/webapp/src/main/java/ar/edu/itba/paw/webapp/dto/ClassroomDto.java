package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Lecture;
import ar.edu.itba.paw.webapp.controller.ClassroomController;
import ar.edu.itba.paw.webapp.controller.PostFileController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;

public class ClassroomDto {

    private String subjectName;
    private Link posts, files;
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
        classroomDto.posts = JaxRsLinkBuilder.linkTo(ClassroomController.class).slash(lecture.getClassId()).slash("posts").withRel(lecture.getClassId().toString());
        classroomDto.files = JaxRsLinkBuilder.linkTo(PostFileController.class).slash(lecture.getClassId()).slash("files").withRel(lecture.getClassId().toString());
        if (lecture.getNotifications() != null)
            classroomDto.notifications = lecture.getNotifications();
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

    public Link getFiles() {
        return files;
    }

    public void setFiles(Link files) {
        this.files = files;
    }

    public Link getPosts() {
        return posts;
    }

    public void setPosts(Link posts) {
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
