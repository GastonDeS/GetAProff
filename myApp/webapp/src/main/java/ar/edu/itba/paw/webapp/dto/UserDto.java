package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Value;

import javax.ws.rs.core.UriInfo;

public class UserDto {

//    @Value("${spring.data.rest.basePath}")
//    private static String apiBaseUrl;

    private String url;

    private String token;

    private String name, description, schedule, mail;

    private Long id;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.description = user.getDescription();
        this.schedule = user.getDescription();
        this.mail = user.getMail();
    }

    public UserDto() {}

    public static UserDto fromUser(UriInfo uri, User user) {
        UserDto userDto = new UserDto();
        userDto.mail = user.getMail();
        userDto.name = user.getName();
        userDto.description = user.getDescription();
        userDto.schedule = user.getSchedule();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
