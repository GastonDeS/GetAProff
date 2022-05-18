package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.controller.UsersController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;

public class StudentDto {

    private String name, mail;

    private Long id;

    private boolean isTeacher;

    public static StudentDto fromUser(User user) {
        StudentDto studentDto = new StudentDto();
        studentDto.mail = user.getMail();
        studentDto.name = user.getName();
        studentDto.id = user.getId();
        studentDto.isTeacher = false;
        return studentDto;
    }

    public boolean isTeacher() {
        return false;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
