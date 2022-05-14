package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Teaches;

import java.util.ArrayList;
import java.util.List;

public class SubjectInfoDto {
    private String subject;

    private Long id;

    private List<Integer> levels, prices;

    public static SubjectInfoDto fromSubjectInfo(String subject, List<Teaches> subjectData) {
        SubjectInfoDto subjectInfoDto = new SubjectInfoDto();
        subjectInfoDto.levels = new ArrayList<>();
        subjectInfoDto.prices = new ArrayList<>();
        subjectInfoDto.id = subjectData.get(0).getSubject().getSubjectId();
        subjectInfoDto.subject = subject;
        subjectData.forEach(teaches -> {
            subjectInfoDto.levels.add(teaches.getLevel());
            subjectInfoDto.prices.add(teaches.getPrice());
        });
        return subjectInfoDto;
    }

    public List<Integer> getLevels() {
        return levels;
    }

    public void setLevels(List<Integer> levels) {
        this.levels = levels;
    }

    public List<Integer> getPrices() {
        return prices;
    }

    public void setPrices(List<Integer> prices) {
        this.prices = prices;
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

