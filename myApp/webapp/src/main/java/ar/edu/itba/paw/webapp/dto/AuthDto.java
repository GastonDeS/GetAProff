package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.controller.UsersController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;


public class AuthDto {

    private Link url;

    public Link getUrl() {
        return url;
    }

    public void setUrl(Link url) {
        this.url = url;
    }

    private String name, mail;

    private Long id;

    private boolean isTeacher;

    public static AuthDto fromUser(User user) {
        AuthDto authDto = new AuthDto();
        authDto.mail = user.getMail();
        authDto.name = user.getName();
        authDto.id = user.getId();
        authDto.isTeacher = user.isTeacher();
        authDto.url = JaxRsLinkBuilder.linkTo(UsersController.class).slash(user.getId()).withRel(user.getId().toString());
        return authDto;
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

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
