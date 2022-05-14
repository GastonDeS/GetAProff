package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Teaches;

import java.util.ArrayList;
import java.util.List;

public class SubjectInfoDto {
    private String subject;

    private Long id;

    private int price, level;

    public static SubjectInfoDto fromSubjectInfo(Teaches teaches) {
        SubjectInfoDto subjectInfoDto = new SubjectInfoDto();
        subjectInfoDto.level = teaches.getLevel();
        subjectInfoDto.price = teaches.getPrice();
        subjectInfoDto.id = teaches.getSubject().getSubjectId();
        subjectInfoDto.subject = teaches.getSubject().getName();
        return subjectInfoDto;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long subjectId) {
        this.id = subjectId;
    }
}

