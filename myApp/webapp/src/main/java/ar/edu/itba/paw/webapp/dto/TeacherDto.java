package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.TeacherInfo;


public class TeacherDto {

    private String name, mail, description, schedule;

    private Long id;

    private int minPrice, maxPrice, reviewsQty;

    private float rate;

    private boolean isTeacher;

    public static TeacherDto getTeacher(TeacherInfo teacher) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.maxPrice = teacher.getMaxPrice();
        teacherDto.minPrice = teacher.getMinPrice();
        teacherDto.rate = teacher.getRate();
        teacherDto.name = teacher.getName();
        teacherDto.schedule = teacher.getSchedule();
        teacherDto.description = teacher.getDescription();
        teacherDto.mail = teacher.getMail();
        teacherDto.id = teacher.getUserId();
        teacherDto.reviewsQty = teacher.getReviews();
        teacherDto.isTeacher = true;
        return teacherDto;
    }

    public boolean isTeacher() {
        return true;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }

    public int getReviewsQty() {
        return reviewsQty;
    }

    public void setReviewsQty(int reviews) {
        this.reviewsQty = reviews;
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
