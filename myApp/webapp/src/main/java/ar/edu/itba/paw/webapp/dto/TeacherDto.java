package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.TeacherInfo;

import javax.ws.rs.core.UriInfo;

public class TeacherDto {

    private String name, mail, description, schedule, url;

    private Long id;

    private int minPrice, maxPrice;

    private float rate;

    public static TeacherDto getTeacher(UriInfo uri, TeacherInfo teacher) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.maxPrice = teacher.getMaxPrice();
        teacherDto.minPrice = teacher.getMinPrice();
        teacherDto.rate = teacher.getRate();
        teacherDto.name = teacher.getName();
        teacherDto.schedule = teacher.getSchedule();
        teacherDto.description = teacher.getDescription();
        teacherDto.mail = teacher.getMail();
        teacherDto.id = teacher.getUserId();
        teacherDto.url = uri.getBaseUriBuilder().path("teachers/").path(String.valueOf(teacher.getUserId())).build().toString();
        return teacherDto;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
