package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.SubjectInfo;

public class SubjectInfoDto {
    private String name;

    private Long id;

    private int price, level;

    public static SubjectInfoDto fromSubjectInfo(SubjectInfo subject) {
        SubjectInfoDto subjectInfoDto = new SubjectInfoDto();
        subjectInfoDto.id = subject.getSubjectId();
        subjectInfoDto.name = subject.getName();
        subjectInfoDto.level = subject.getLevel();
        subjectInfoDto.price = subject.getPrice();
        return subjectInfoDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
