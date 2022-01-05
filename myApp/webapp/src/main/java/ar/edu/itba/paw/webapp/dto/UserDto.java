package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;

import javax.ws.rs.core.UriInfo;

public class UserDto {

    private String url;

    private String mail;

    public static UserDto fromUser(UriInfo uri, User user) {
        UserDto userDto = new UserDto();
        userDto.mail = user.getMail();
        userDto.url = uri.getBaseUriBuilder().path("users").path(String.valueOf(user.getId())).build().toString();
        return userDto;
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
}
