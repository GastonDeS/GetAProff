package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Value;

import javax.ws.rs.core.UriInfo;

public class UserDto {

//    @Value("${spring.data.rest.basePath}")
//    private static String apiBaseUrl;

    private String url;

    private String mail;

    private String token;

    public static UserDto fromUser(UriInfo uri, User user) {
        UserDto userDto = new UserDto();
        userDto.mail = user.getMail();
        userDto.url = uri.getBaseUriBuilder().path("api/users").path(String.valueOf(user.getId())).build().toString();
        return userDto;
    }

    public static UserDto login(UriInfo uri, User user, String token) {
        UserDto userDto = new UserDto();
        userDto.token = token;
        userDto.mail = user.getMail();
        userDto.url = uri.getBaseUriBuilder().path("api/auth").path(user.getMail()).build().toString();
        return userDto;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
