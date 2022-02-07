package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;

import javax.ws.rs.core.UriInfo;

public class AuthDto {

    private String token, url;

    private boolean isTeacher;

    private Long id;

    public static AuthDto login(UriInfo uri, User user, String token) {
        AuthDto authDto = new AuthDto();
        authDto.id = user.getId();
        authDto.token = token;
        authDto.isTeacher = user.isTeacher();
        String urlStr = user.isTeacher() ? "teachers" : "students";
        authDto.url = uri.getBaseUriBuilder().path(urlStr).path(user.getId().toString()).build().toString();
        return authDto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
