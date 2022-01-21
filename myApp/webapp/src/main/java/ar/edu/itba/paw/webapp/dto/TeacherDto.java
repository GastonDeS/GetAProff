package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.CardProfile;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

public class TeacherDto {

    private String url;

    private int minPrice, maxPrice;

    private float rate;

    private UserDto user;

    private Long userId;

    public static GenericEntity<List<TeacherDto>> topRatedTeachers(UriInfo uri, List<CardProfile> teachersCardProfile) {
        List<TeacherDto> topRatedTeacherDto = new ArrayList<>();
        teachersCardProfile.forEach(teacher -> {
            TeacherDto teacherDto = new TeacherDto();
            teacherDto.url = uri.getBaseUriBuilder().path("api/users").path(String.valueOf(teacher.getUserId())).build().toString();
            teacherDto.maxPrice = teacher.getMaxPrice();
            teacherDto.minPrice = teacher.getMinPrice();
            teacherDto.rate = teacher.getRate();
            teacherDto.userId = teacher.getUserId();
            topRatedTeacherDto.add(teacherDto);
        });
        return new GenericEntity<List<TeacherDto>>(topRatedTeacherDto){};
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
