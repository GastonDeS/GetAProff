package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Teaches;

import java.util.ArrayList;
import java.util.List;

public class SubjectInfoDto {
    private String name;

    private Long teacherId, id;

    private List<Integer> levels, prices;

    public static SubjectInfoDto fromSubjectInfo(String subject, List<Teaches> subjectData) {
        SubjectInfoDto subjectInfoDto = new SubjectInfoDto();
        subjectInfoDto.levels = new ArrayList<>();
        subjectInfoDto.prices = new ArrayList<>();
        subjectInfoDto.teacherId = subjectData.get(0).getTeacher().getId();
        subjectInfoDto.id = subjectData.get(0).getSubject().getSubjectId();
        subjectInfoDto.name = subject;
        subjectData.forEach(teaches -> {
            subjectInfoDto.levels.add(teaches.getLevel());
            subjectInfoDto.prices.add(teaches.getPrice());
        });
        return subjectInfoDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String subjectName) {
        this.name = subjectName;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long subjectId) {
        this.id = subjectId;
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
}

