package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Teaches;

public class SubjectInfoDto {
    private String subject;

    private Long id;

    private int price, level;

    public static SubjectInfoDto fromSubjectInfo(Teaches subject) {
        SubjectInfoDto subjectInfoDto = new SubjectInfoDto();
        subjectInfoDto.id = subject.getTeacher().getId();
        subjectInfoDto.subject = subject.getSubject().getName();
        subjectInfoDto.level = subject.getLevel();
        subjectInfoDto.price = subject.getPrice();
        return subjectInfoDto;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
