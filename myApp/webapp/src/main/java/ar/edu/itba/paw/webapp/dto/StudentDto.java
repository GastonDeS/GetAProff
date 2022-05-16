package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.controller.UsersController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;

import javax.ws.rs.core.UriInfo;

public class StudentDto {

//    @Value("${spring.data.rest.basePath}")
//    private static String apiBaseUrl;

    private Link url;

    private String name, mail;

    private Long id;

    private boolean isTeacher;

    public static StudentDto fromUser(User user) {
        StudentDto studentDto = new StudentDto();
        studentDto.mail = user.getMail();
        studentDto.name = user.getName();
        studentDto.id = user.getId();
        studentDto.isTeacher = user.isTeacher();
        studentDto.url = JaxRsLinkBuilder.linkTo(UsersController.class).slash(user.getId()).withSelfRel();
        return studentDto;
    }

    public boolean isTeacher() {
        return isTeacher;
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

    public Link getUrl() {
        return url;
    }

    public void setUrl(Link url) {
        this.url = url;
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
