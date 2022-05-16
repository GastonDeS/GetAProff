package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.controller.UsersController;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;

import javax.ws.rs.core.UriInfo;

public class StudentDto {

//    @Value("${spring.data.rest.basePath}")
//    private static String apiBaseUrl;

    private String url;

    private String name, mail;

    private Long id;

    private boolean isTeacher;

    public static StudentDto fromUser(User user) {
        StudentDto studentDto = new StudentDto();
        studentDto.mail = user.getMail();
        studentDto.name = user.getName();
        studentDto.id = user.getId();
        studentDto.isTeacher = user.isTeacher();
        //studentDto.url = uri.getBaseUriBuilder().path("users").path(String.valueOf(user.getId())).build().toString();
        studentDto.url = JaxRsLinkBuilder.linkTo(UsersController.class).slash(user.getId()).withSelfRel().toString();
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
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
