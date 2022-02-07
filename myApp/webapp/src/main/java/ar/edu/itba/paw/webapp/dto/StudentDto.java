package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;

import javax.ws.rs.core.UriInfo;

public class StudentDto {

//    @Value("${spring.data.rest.basePath}")
//    private static String apiBaseUrl;

    private String url;

    private String name, mail;

    private Long id;

    public static StudentDto fromUser(UriInfo uri, User user) {
        StudentDto studentDto = new StudentDto();
        studentDto.mail = user.getMail();
        studentDto.name = user.getName();
        studentDto.url = uri.getBaseUriBuilder().path("users").path(String.valueOf(user.getId())).build().toString();
        return studentDto;
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
